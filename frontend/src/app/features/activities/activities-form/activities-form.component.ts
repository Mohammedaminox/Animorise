import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivitiesService, Activity } from '../../../core/services/activities.service';
import { NgForOf } from '@angular/common';
import { AnimalService, Animal } from '../../../core/services/animal.service';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { ActivityTypeService, ActivityType } from '../../../core/services/activityType.service';
@Component({
  selector: 'app-activities-form',
  templateUrl: './activities-form.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  styleUrls: ['./activities-form.component.css']
})
export class ActivitiesFormComponent implements OnInit {
  activitiesForm!: FormGroup;
  animalsList: Animal[] = [];
  activityTypesList: ActivityType[] = [];

  private animalService = inject(AnimalService);
  private authService = inject(AuthService);
  private activityTypeService = inject(ActivityTypeService);
  private router = inject(Router);

  private userId?: number | null;

  constructor(private fb: FormBuilder, private activitiesService: ActivitiesService) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCurrentUser();
    this.loadActivityTypes();
  }

  initForm(): void {
    this.activitiesForm = this.fb.group({
      animalId: ['', Validators.required],
      activityTypeId: ['', Validators.required],
      description: ['', Validators.required],
      repeat: [false, Validators.required],
      scheduleStart: ['', Validators.required],
      repeatEvery: [0, Validators.required],
      repeatUnit: ['', Validators.required],
      userId: [''] // Champ caché pour l'ID de l'utilisateur
    });
  }

  private loadCurrentUser(): void {
    this.userId = this.authService.getCurrentUserId();
    if (this.userId !== null) {
      this.activitiesForm.patchValue({ userId: this.userId });
      this.loadAnimals();
    } else {
      console.error('User ID is not available in session storage.');
    }
  }

  private loadAnimals(): void {
    if (this.userId !== null) {
      this.animalService.getAnimalsByOwnerId(this.userId!).subscribe(animals => {
        this.animalsList = animals;
      });
    }
  }
  private loadActivityTypes(): void {
    this.activityTypeService.getAllActivityType().subscribe(activityTypes => {
      this.activityTypesList = activityTypes;
    });
  }

  onSubmit(): void {
    if (this.activitiesForm.invalid) return;

    const activity: Activity = this.activitiesForm.value;
    this.activitiesService.addActivity(activity).subscribe({
      next: () => this.router.navigate(['/dashboard/activities']),
      error: (err) => console.error('Error occurred:', err)
    });
  }
}
