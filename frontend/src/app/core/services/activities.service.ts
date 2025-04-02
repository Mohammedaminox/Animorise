import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Activity {
  id?: number;
  animalId: number;
  animalName?: string;
  activityTypeId: number;
  activityTypeName?: string;
  description: string;
  repeat: boolean;
  scheduleStart: string;
  repeatEvery: number;
  repeatUnit: string;
  notified?: boolean;
  userEmail?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {
  private API_URL = 'http://localhost:8088/api/activities';

  constructor(private http: HttpClient) {}

  addActivity(activity: Activity): Observable<Activity> {
    return this.http.post<Activity>(this.API_URL, activity);
  }

  getActivitiesByOwner(ownerId: number): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${this.API_URL}/owner/${ownerId}`);
  }
}
