import { Component, OnInit, inject } from '@angular/core';
import { ActivitiesService, Activity } from '../../../core/services/activities.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe, NgClass, NgForOf, NgIf} from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-activities-list',
  standalone: true,
  templateUrl: './activities-list.component.html',
  imports: [
    NgForOf,
    NgIf,
    DatePipe,
    NgClass
  ],
  styleUrls: ['./activities-list.component.css']
})
export class ActivitiesListComponent implements OnInit {
  activities: Activity[] = [];
  errorMessage: string | null = null;

  private activitiesService = inject(ActivitiesService);
  private router = inject(Router);
  private authService = inject(AuthService);

  ngOnInit(): void {
    const ownerId = this.authService.getCurrentUserId();
    if (ownerId !== null) {
      this.getActivitiesByOwner(ownerId);
    } else {
      this.errorMessage = 'User ID is not available in session storage.';
    }
  }

  getActivitiesByOwner(ownerId: number): void {
    this.activitiesService.getActivitiesByOwner(ownerId).pipe(
      catchError(error => {
        this.errorMessage = 'Error fetching activities';
        return of([]);
      })
    ).subscribe((activities) => {
      this.activities = activities;
      this.errorMessage = null;
    });
  }

  openActivitiesForm() {
    this.router.navigate(['/dashboard/activities/form']); // Navigate to add form
  }
}
