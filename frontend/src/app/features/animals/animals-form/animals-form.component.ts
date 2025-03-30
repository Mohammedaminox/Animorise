// animals-form.component.ts
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AnimalService, Animal } from '../../../core/services/animal.service';
import { SpeciesService, Species } from '../../../core/services/species.service';
import { AuthService } from '../../../core/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForOf, NgOptimizedImage } from "@angular/common";

@Component({
  selector: 'app-animals-form',
  standalone: true,
  templateUrl: './animals-form.component.html',
  imports: [ReactiveFormsModule, NgForOf, NgOptimizedImage],
  styleUrls: ['./animals-form.component.css']
})
export class AnimalsFormComponent implements OnInit {
  animalForm!: FormGroup;
  animalId?: number;
  speciesList: Species[] = [];
  // genders = ['Male', 'Female'];
  // healthStatuses = ['Healthy', 'Sick', 'UnderTreatment'];
  currentUserId?: number | null;

  private animalService = inject(AnimalService);
  private speciesService = inject(SpeciesService);
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  constructor() {}

  ngOnInit(): void {
    this.initForm();
    this.loadSpecies();
    this.loadCurrentUser();
  }

  private initForm(): void {
    this.animalForm = this.fb.group({
      name: ['', Validators.required],
      race: ['', Validators.required],
      gender: ['', Validators.required],
      vaccinated: [false],
      healthStatus: ['', Validators.required],
      birthDate: ['', Validators.required],
      speciesId: [null, Validators.required],
      ownerId: [null]
    });

    this.checkEditMode();
  }

  private checkEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.animalId = +params['id'];

        this.animalService.getAnimalById(this.animalId).subscribe(animal => {
          if (animal) {
            this.animalForm.patchValue({
              name: animal.name,
              race: animal.race,
              gender: animal.gender,
              vaccinated: animal.vaccinated,
              healthStatus: animal.healthStatus,
              birthDate: animal.birthDate,
              speciesId: animal.speciesId,
              ownerId: animal.ownerId
            });
          }
        });
      }
    });
  }

  private loadSpecies(): void {
    this.speciesService.getAllSpecies().subscribe(species => {
      this.speciesList = species;
    });
  }

  private loadCurrentUser(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    if (this.currentUserId !== null) {
      this.animalForm.patchValue({ ownerId: this.currentUserId });
    } else {
      console.error('User ID is not available in session storage.');
    }
  }


  onSubmit(): void {
    if (this.animalForm.invalid) return;
    console.log(this.animalForm.value)
    const animal: Animal = this.animalForm.value;
    if (!this.currentUserId) {
      console.error('User ID is not available.');
      return;
    }

    const request = this.animalId
      ? this.animalService.updateAnimal(this.animalId, animal)
      : this.animalService.addAnimal(this.currentUserId, animal);

    request.subscribe({
      next: () => this.router.navigate(['/dashboard/animals']),
      error: (err) => console.error('Error occurred:', err)
    });
  }
}
