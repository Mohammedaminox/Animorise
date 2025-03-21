import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { Router } from '@angular/router';

interface AuthResponse {
  token: string;
  expiresIn: number; // Time in seconds
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8088/auth';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object // Injecting platformId
  ) {
    if (isPlatformBrowser(this.platformId)) {
      this.isAuthenticatedSubject.next(this.hasValidSession());
    }
  }

  private hasValidSession(): boolean {
    if (isPlatformBrowser(this.platformId)) { // Check if running in browser
      const token = sessionStorage.getItem('token');
      const expiresAt = sessionStorage.getItem('expiresAt');
      return token && expiresAt ? new Date().getTime() < Number(expiresAt) : false;
    }
    return false;
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/login`, { email, password }).pipe(
      tap((response) => this.storeSession(response)),
      catchError(this.handleError)
    );
  }

  register(fullName: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.API_URL}/signup`, { fullName, email, password }).pipe(
      catchError(this.handleError)
    );
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.clear();
    }
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/auth/login']);
  }

  isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  private storeSession(response: AuthResponse): void {
    if (isPlatformBrowser(this.platformId)) {
      const expirationTime = new Date().getTime() + response.expiresIn * 1000;
      sessionStorage.setItem('token', response.token);
      sessionStorage.setItem('expiresAt', expirationTime.toString());
    }
    this.isAuthenticatedSubject.next(true);
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let message = 'Something went wrong!';
    if (error.error?.message) {
      message = error.error.message;
    } else if (error.status === 0) {
      message = 'Cannot connect to the server. Please try again later.';
    }
    return throwError(() => new Error(message));
  }
}
