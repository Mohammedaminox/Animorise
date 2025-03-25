import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Species {
  id?: number;
  name: string;
  icon?: File;
  iconPath?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SpeciesService {
  private API_URL = 'http://localhost:8088/api/species'; // Adjust API URL

  private http = inject(HttpClient);

  getAllSpecies(): Observable<Species[]> {
    return this.http.get<Species[]>(this.API_URL);
  }

  getSpeciesById(id: number): Observable<Species> {
    return this.http.get<Species>(`${this.API_URL}/${id}`);
  }

  addSpecies(species: Species, file?: File): Observable<Species> {
    const formData = new FormData();
    formData.append('name', species.name);
    if (file) formData.append('icon', file);

    return this.http.post<Species>(this.API_URL, formData);
  }

  updateSpecies(id: number, species: Species, file?: File): Observable<Species> {
    const formData = new FormData();
    formData.append('name', species.name);
    if (file) formData.append('icon', file);

    return this.http.put<Species>(`${this.API_URL}/${id}`, formData);
  }

  deleteSpecies(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
