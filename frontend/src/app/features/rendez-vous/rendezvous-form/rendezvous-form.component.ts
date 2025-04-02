import {Component, OnInit, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { RendezVousService, RendezVous } from '../../../core/services/rendezvous.service';
import {NgForOf} from "@angular/common";
import {AnimalService, Animal} from '../../../core/services/animal.service';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-rendezvous-form',
  templateUrl: './rendezvous-form.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  styleUrls: ['./rendezvous-form.component.css']
})
export class RendezvousFormComponent implements OnInit {
  rendezVousForm!: FormGroup;
  animalsList: Animal[] = [];
  private animalService = inject(AnimalService);
  private authService = inject(AuthService);
  private router = inject(Router);

  private userId?: number | null;

  constructor(private fb: FormBuilder, private rendezVousService: RendezVousService) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCurrentUser();
  }

  initForm(): void {
    this.rendezVousForm = this.fb.group({
      animalId: ['', Validators.required],
      dateTime: ['', Validators.required],
      status: ['PENDING', Validators.required],
      userId: [''] // Champ caché pour l'ID de l'utilisateur
    });
  }

  private loadCurrentUser(): void {
    this.userId = this.authService.getCurrentUserId();
    if (this.userId !== null) {
      this.rendezVousForm.patchValue({ userId: this.userId });
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

  onSubmit(): void {
    if (this.rendezVousForm.invalid) return;

    const rendezVous: RendezVous = this.rendezVousForm.value;
    this.rendezVousService.createRendezVous(rendezVous).subscribe({
      next: () => this.router.navigate(['/dashboard/rendezvous']),
      error: (err) => console.error('Error occurred:', err)
    });
  }
}
