import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {Router} from "@angular/router";
import {ActivityTypeService, ActivityType} from "../../../core/services/activityType.service";

@Component({
  selector: 'app-activityType-list',
  standalone: true,
  templateUrl: './activity-type-list.component.html',
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage
  ],
  styleUrls: ['./activity-type-list.component.css']
})
export class ActivityTypeListComponent implements   OnInit {
  activityTypeList: ActivityType[] = [];

  private router = inject(Router);
  private activityTypeService = inject(ActivityTypeService);

  ngOnInit(): void {
    this.loadActivityType();
  }

  loadActivityType(): void {
    this.activityTypeService.getAllActivityType().subscribe((data) => (this.activityTypeList = data));
  }

  deleteActivityType(id: number): void {
    if (confirm('Are you sure you want to delete this ActivityType?')) {
      this.activityTypeService.deleteActivityType(id).subscribe(() => {
        this.activityTypeList = this.activityTypeList.filter(s => s.id !== id);
      });
    }
  }
  openActivityTypeForm() {
    this.router.navigate(['/dashboard/activityType/form']); // Navigate to add form
  }

  editActivityType(id: number) {
    this.router.navigate(['/dashboard/activityType/form', id]);

  }
}
