import { Component, inject, OnInit } from '@angular/core';
import { NgClass, NgForOf, NgIf } from "@angular/common";
import { Animal, AnimalService } from "../../../core/services/animal.service";
import { Router } from "@angular/router";
import { catchError } from "rxjs/operators";
import { of } from "rxjs";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-animals-veterinere',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgClass,
    FormsModule
  ],
  templateUrl: './animals-veterinere.component.html',
  styleUrl: './animals-veterinere.component.css'
})
export class AnimalsVeterinereComponent implements OnInit {
  animalsList: Animal[] = [];
  errorMessage: string | null = null;
  showHealthStatusModal: boolean = false;
  showVaccinationStatusModal: boolean = false;
  newHealthStatus: string = '';
  selectedAnimal: Animal | null = null;

  private router = inject(Router);
  private animalService = inject(AnimalService);

  ngOnInit(): void {
    this.loadAnimals();
  }

  loadAnimals(): void {
    this.animalService.getAllAnimals().pipe(
      catchError(error => {
        this.errorMessage = 'Failed to load animals';
        return of([]);
      })
    ).subscribe((data) => {
      this.animalsList = data;
      this.errorMessage = null;
    });
  }

  openHealthStatusModal(animal: Animal): void {
    this.selectedAnimal = animal;
    this.newHealthStatus = animal.healthStatus;
    this.showHealthStatusModal = true;
  }

  closeHealthStatusModal(): void {
    this.showHealthStatusModal = false;
  }

  updateHeathStatus(): void {
    if (this.selectedAnimal && this.newHealthStatus !== null) {
      this.animalService.updateHealthStatus(this.selectedAnimal.id!, this.newHealthStatus).subscribe(updatedAnimal => {
        this.selectedAnimal!.healthStatus = updatedAnimal.healthStatus;
        this.closeHealthStatusModal();
      }, error => {
        console.error('Failed to update health status', error);
      });
    }
  }

  openVaccinationStatusModal(animal: Animal): void {
    this.selectedAnimal = animal;
    this.showVaccinationStatusModal = true;
  }

  confirmVaccinationStatus(isVaccinated: boolean): void {
    if (this.selectedAnimal) {
      this.animalService.updateVaccinationStatus(this.selectedAnimal.id!, isVaccinated).subscribe(updatedAnimal => {
        this.selectedAnimal!.vaccinated = updatedAnimal.vaccinated;
        this.showVaccinationStatusModal = false;
      }, error => {
        console.error('Failed to update vaccination status', error);
      });
    }
  }
}
