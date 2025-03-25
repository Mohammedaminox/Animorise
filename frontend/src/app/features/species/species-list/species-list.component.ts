import { Component, OnInit, inject } from '@angular/core';
import { SpeciesService, Species } from '../../../core/services/species.service';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import { Router } from '@angular/router'; // ✅ Import Router

@Component({
  selector: 'app-species-list',
  standalone: true,
  templateUrl: './species-list.component.html',
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage
  ],
  styleUrls: ['./species-list.component.css']
})
export class SpeciesListComponent implements OnInit {
  speciesList: Species[] = [];

  private router = inject(Router);
  private speciesService = inject(SpeciesService);

  ngOnInit(): void {
    this.loadSpecies();
  }

  loadSpecies(): void {
    this.speciesService.getAllSpecies().subscribe((data) => (this.speciesList = data));
  }

  deleteSpecies(id: number): void {
    if (confirm('Are you sure you want to delete this species?')) {
      this.speciesService.deleteSpecies(id).subscribe(() => {
        this.speciesList = this.speciesList.filter(s => s.id !== id);
      });
    }
  }
  openSpeciesForm() {
    this.router.navigate(['/dashboard/species/form']); // Navigate to add form
  }

  editSpecies(id: number) {
    this.router.navigate(['/dashboard/species/form', id]);

  }
}
