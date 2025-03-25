import { Component, OnInit, inject } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { SpeciesService, Species } from '../../../core/services/species.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-species-form',
  standalone: true,
  templateUrl: './species-form.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./species-form.component.css']
})
export class SpeciesFormComponent implements OnInit {
  speciesForm!: FormGroup;
  speciesId?: number;
  selectedFile?: File;

  private speciesService = inject(SpeciesService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  constructor() {}

  ngOnInit(): void {
    this.initForm();
    this.checkEditMode();
  }

  private initForm(): void {
    this.speciesForm = this.fb.group({
      name: ['', Validators.required],
      icon: [null]
    });
  }

  private checkEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.speciesId = +params['id'];
        this.speciesService.getSpeciesById(this.speciesId).subscribe(species => {
          this.speciesForm.patchValue(species);
        });
      }
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  onSubmit(): void {
    if (this.speciesForm.invalid) return;

    const species: Species = this.speciesForm.value;
    const request = this.speciesId
      ? this.speciesService.updateSpecies(this.speciesId, species, this.selectedFile)
      : this.speciesService.addSpecies(species, this.selectedFile);

    request.subscribe(() => this.router.navigate(['/dashboard/species']));
  }
}
