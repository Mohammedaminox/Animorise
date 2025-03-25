import { Routes } from '@angular/router';
import { LandingComponent } from './features/landing-page/landing/landing.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () => import('./features/auth/login/login.component')
          .then(c => c.LoginComponent)
      },
      {
        path: 'register',
        loadComponent: () => import('./features/auth/register/register.component')
          .then(c => c.RegisterComponent)
      }
    ]
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () => import('./dashboard/dashboard/dashboard.component')
      .then(c => c.DashboardComponent),

      children: [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadComponent: () => import('./dashboard/home/home.component')
      .then(c => c.HomeComponent)
  },
      {
        path: 'species',
        canActivate: [authGuard],
        children: [
          {
            path: '',
            loadComponent: () => import('./features/species/species-list/species-list.component')
              .then(c => c.SpeciesListComponent)
          },
          {
            path: 'form',
            loadComponent: () => import('./features/species/species-form/species-form.component')
              .then(c => c.SpeciesFormComponent)
          },
          {
            path: 'form/:id',
            loadComponent: () => import('./features/species/species-form/species-form.component')
              .then(c => c.SpeciesFormComponent)
          }
        ]
      },

        {
          path: 'activityType',
          canActivate: [authGuard],
          children: [
            {
              path: '',
              loadComponent: () => import('./features/activity-type/activity-type-list/activity-type-list.component')
                .then(c => c.ActivityTypeListComponent)
            },
            {
              path: 'form',
              loadComponent: () => import('./features/activity-type/activity-type-form/activity-type-form.component')
                .then(c => c.ActivityTypeFormComponent)
            },
            {
              path: 'form/:id',
              loadComponent: () => import('./features/activity-type/activity-type-form/activity-type-form.component')
                .then(c => c.ActivityTypeFormComponent)
            }
          ]
        }
      ]
  }
];
