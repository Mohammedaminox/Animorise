import {Component, inject, OnInit} from '@angular/core';
import { RendezVousService, RendezVous } from '../../../core/services/rendezvous.service';
import { AuthService } from '../../../core/services/auth.service';
import {DatePipe, NgClass, NgForOf} from "@angular/common";
import {RendezvousFormComponent} from "../rendezvous-form/rendezvous-form.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-rendezvous',
  standalone: true,
  templateUrl: './rendezvous.component.html',
  styleUrls: ['./rendezvous.component.css'],
  imports: [
    DatePipe,
    NgForOf,
    NgClass,
    RendezvousFormComponent
  ]
})
export class RendezVousComponent implements OnInit {
  rendezVousList: RendezVous[] = [];
  currentUserId?: number | null;

  private router = inject(Router);
  constructor(private rendezVousService: RendezVousService, private authService: AuthService) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    if (this.currentUserId !== null) {
      this.loadRendezVousByUser(this.currentUserId);
    } else {
      console.error('User ID is not available in session storage.');
    }
  }

  loadRendezVousByUser(userId: number): void {
    this.rendezVousService.getRendezVousByUser(userId).subscribe((data: RendezVous[])  => {
      this.rendezVousList = data;
    });
  }

  openRendezvousForm() {
    this.router.navigate(['/dashboard/rendezvous/form']); // Navigate to add form
  }


}
