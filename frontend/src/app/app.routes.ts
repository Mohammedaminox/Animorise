import { Routes } from '@angular/router';
import { LandingComponent } from './features/landing-page/landing/landing.component';
import { UnauthorizedComponent } from './features/unauthorized/unauthorized.component';
import { authGuard } from './core/guards/auth.guard';
import { veterinereGuard } from './core/guards/veterinere.guard';
import {RendezVousComponent} from "./features/rendez-vous/rendezvous/rendezvous.component";
import {RendezVousVeterinereComponent} from "./features/rendez-vous/rendezvous-veterinere/rendezvous-veterinere.component"; // Import the guard
import { RendezvousFormComponent } from './features/rendez-vous/rendezvous-form/rendezvous-form.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  },

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
          path: 'rendezvous',
          canActivate: [authGuard],
          children: [
            {
              path: '',
              loadComponent: () => import('./features/rendez-vous/rendezvous/rendezvous.component').then(c => c.RendezVousComponent)
            },
            {
              path: 'veterinere',
              loadComponent: () => import('./features/rendez-vous/rendezvous-veterinere/rendezvous-veterinere.component').then(c => c.RendezVousVeterinereComponent),
              canActivate: [authGuard, veterinereGuard]
            },
            {
              path: 'form',
              loadComponent: () => import('./features/rendez-vous/rendezvous-form/rendezvous-form.component').then(c => c.RendezvousFormComponent)
            }
          ]
        },

      {
        path: 'species',
        canActivate: [authGuard, veterinereGuard],
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
          canActivate: [authGuard, veterinereGuard],          children: [
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
        },

        {
          path: 'animals',
          canActivate: [authGuard],
          children: [
            {
              path: '',
              loadComponent: () => import('./features/animals/animals-list/animals-list.component')
                .then(c => c.AnimalsListComponent)
            },
            {
              path: 'form',
              loadComponent: () => import('./features/animals/animals-form/animals-form.component')
                .then(c => c.AnimalsFormComponent)
            },
            {
              path: 'form/:id',
              loadComponent: () => import('./features/animals/animals-form/animals-form.component')
                .then(c => c.AnimalsFormComponent)
            }
          ]
        }
      ]
  }
];
