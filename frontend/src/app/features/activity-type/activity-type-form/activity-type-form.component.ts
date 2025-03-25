import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ActivityTypeService, ActivityType} from "../../../core/services/activityType.service";

@Component({
  selector: 'app-activity-type-form',
  standalone: true,
    imports: [
        ReactiveFormsModule
    ],
  templateUrl: './activity-type-form.component.html',
  styleUrl: './activity-type-form.component.css'
})
export class ActivityTypeFormComponent implements OnInit{
  activityTypeForm!: FormGroup;
  activityTypeId?: number;
  selectedFile?: File;

  private activityTypeService = inject(ActivityTypeService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  constructor() {}

  ngOnInit(): void {
    this.initForm();
    this.checkEditMode();
  }

  private initForm(): void {
    this.activityTypeForm = this.fb.group({
      name: ['', Validators.required],
      icon: [null]
    });
  }

  private checkEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.activityTypeId = +params['id'];
        this.activityTypeService.getActivityTypeById(this.activityTypeId).subscribe(activityType => {
          this.activityTypeForm.patchValue(activityType);
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
    if (this.activityTypeForm.invalid) return;

    const activityType: ActivityType = this.activityTypeForm.value;
    const request = this.activityTypeId
      ? this.activityTypeService.updateActivityType(this.activityTypeId, activityType, this.selectedFile)
      : this.activityTypeService.addActivityType(activityType, this.selectedFile);

    request.subscribe(() => this.router.navigate(['/dashboard/activityType']));
  }
}
