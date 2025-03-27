import { Component, OnInit, inject } from '@angular/core';
import { AnimalService, Animal } from '../../../core/services/animal.service';
import { NgForOf, NgIf, NgOptimizedImage } from '@angular/common';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-animals-list',
  standalone: true,
  templateUrl: './animals-list.component.html',
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage
  ],
  styleUrls: ['./animals-list.component.css']
})
export class AnimalsListComponent implements OnInit {
  animalsList: Animal[] = [];
  errorMessage: string | null = null;

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

  deleteAnimal(id: number): void {
    if (confirm('Are you sure you want to delete this animal?')) {
      this.animalService.deleteAnimal(id).pipe(
        catchError(error => {
          this.errorMessage = 'Failed to delete animal';
          return of(null);
        })
      ).subscribe(() => {
        this.animalsList = this.animalsList.filter(a => a.id !== id);
        this.errorMessage = null;
      });
    }
  }

  openAnimalForm() {
    this.router.navigate(['/dashboard/animals/form']); // Navigate to add form
  }

  editAnimal(id: number) {
    this.router.navigate(['/dashboard/animals/form', id]);
  }
}
