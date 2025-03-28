// auth.service.ts
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { Router } from '@angular/router';

interface AuthResponse {
  token: string;
  expiresIn: number; // Time in seconds
  user: { id: number; role: string }; // Include user ID
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8088/auth';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private userSubject = new BehaviorSubject<{ id: number; role: string } | null>(null); // Include user ID

  user$ = this.userSubject.asObservable(); // Expose user$ observable

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object // Injecting platformId
  ) {
    if (isPlatformBrowser(this.platformId)) {
      this.isAuthenticatedSubject.next(this.hasValidSession());
      this.userSubject.next(this.getUserFromSession()); // Initialize user subject
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

  private getUserFromSession(): { id: number; role: string } | null {
    if (isPlatformBrowser(this.platformId)) {
      const user = sessionStorage.getItem('user');
      return user ? JSON.parse(user) : null;
    }
    return null;
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/login`, { email, password }).pipe(
      tap((response) => {
        console.log('AuthResponse:', response); // Log the response
        this.storeSession(response);
      }),
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
    this.userSubject.next(null); // Clear user subject
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
      sessionStorage.setItem('user', JSON.stringify(response.user)); // Store user information
    }
    this.isAuthenticatedSubject.next(true);
    this.userSubject.next(response.user); // Update user subject
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

// auth.service.ts
  getCurrentUserId(): number | null {
    const user = sessionStorage.getItem('user');
    return user ? JSON.parse(user).id : null;
  }
}
