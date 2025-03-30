import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RendezVous {
  id: number;
  animalId: number;
  animalName: string;
  animalRace: string;
  animalGender: string;
  animalVaccinated: boolean;
  animalHealthStatus: string;
  dateTime: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class RendezVousService {
  private API_URL = 'http://localhost:8088/api/rendezvous';

  constructor(private http: HttpClient) {}

  getRendezVousByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/user/${userId}`);
  }
  getAllRendezVous(): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(this.API_URL);
  }

  createRendezVous(rendezVous: RendezVous): Observable<RendezVous> {
    return this.http.post<RendezVous>(this.API_URL, rendezVous);
  }

  acceptRendezVous(rendezVousId: number): Observable<void> {
    return this.http.put<void>(`${this.API_URL}/${rendezVousId}/accept`, {});
  }
  cancelRendezVous(rendezVousId: number): Observable<void> {
    return this.http.put<void>(`${this.API_URL}/${rendezVousId}/cancel`, {});
  }

}
