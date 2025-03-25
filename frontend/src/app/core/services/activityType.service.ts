import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ActivityType {
  id?: number;
  name: string;
  icon?: File;
  iconPath?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ActivityTypeService {
  private API_URL = 'http://localhost:8088/api/activity-types'; // Adjust API URL

  private http = inject(HttpClient);

  getAllActivityType(): Observable<ActivityType[]> {
    return this.http.get<ActivityType[]>(this.API_URL);
  }

  getActivityTypeById(id: number): Observable<ActivityType> {
    return this.http.get<ActivityType>(`${this.API_URL}/${id}`);
  }

  addActivityType(species: ActivityType, file?: File): Observable<ActivityType> {
    const formData = new FormData();
    formData.append('name', species.name);
    if (file) formData.append('icon', file);

    return this.http.post<ActivityType>(this.API_URL, formData);
  }

  updateActivityType(id: number, species: ActivityType, file?: File): Observable<ActivityType> {
    const formData = new FormData();
    formData.append('name', species.name);
    if (file) formData.append('icon', file);

    return this.http.put<ActivityType>(`${this.API_URL}/${id}`, formData);
  }

  deleteActivityType(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
