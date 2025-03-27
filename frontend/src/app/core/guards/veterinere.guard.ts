import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

export const veterinereGuard = () => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return authService.user$.pipe(
    map(user => {
      if (user && user.role === 'VETERINERE') {
        return true;
      } else {
        router.navigate(['/unauthorized'], { queryParams: { message: 'This is only for veterinere' } });
        return false;
      }
    })
  );
};
