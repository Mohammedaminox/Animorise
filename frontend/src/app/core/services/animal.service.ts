import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Animal {
  id?: number;
  name: string;
  speciesId: number;
  speciesName: string;
  speciesIcon: string;
  race: string;
  gender: string;
  vaccinated: boolean;
  healthStatus: string;
  photoUrl?: string;
  birthDate: string;
  ownerId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AnimalService {
  private API_URL = 'http://localhost:8088/api/animals'; // Adjust API URL

  private http = inject(HttpClient);

  getAllAnimals(): Observable<Animal[]> {
    return this.http.get<Animal[]>(this.API_URL);
  }

  getAnimalById(id: number): Observable<Animal> {
    return this.http.get<Animal>(`${this.API_URL}/${id}`);
  }

  addAnimal(ownerId: number, animal: Animal): Observable<Animal> {
    return this.http.post<Animal>(`${this.API_URL}/${ownerId}`, animal);
  }
  updateAnimal(id: number, animal: Animal, selectedFile: File | undefined): Observable<Animal> {
    return this.http.put<Animal>(`${this.API_URL}/${id}`, animal);
  }

  deleteAnimal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  updateHealthStatus(id: number, healthStatus: string): Observable<Animal> {
    return this.http.put<Animal>(`${this.API_URL}/${id}/health-status`, { healthStatus });
  }

  updateVaccinationStatus(id: number, vaccinated: boolean): Observable<Animal> {
    return this.http.put<Animal>(`${this.API_URL}/${id}/vaccinated`, { vaccinated });
  }
}
