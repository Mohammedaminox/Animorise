import { Component, OnInit } from '@angular/core';
import { RendezVousService, RendezVous } from '../../../core/services/rendezvous.service';
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-rendezvous-veterinere',
  standalone: true,
  templateUrl: './rendezvous-veterinere.component.html',
  styleUrls: ['./rendezvous-veterinere.component.css'],
  imports: [
    DatePipe,
    NgForOf,
    NgClass,
    NgIf
  ]
})
export class RendezVousVeterinereComponent implements OnInit {
  rendezVousList: RendezVous[] = [];


  constructor(private rendezVousService: RendezVousService) {}

  ngOnInit(): void {
    this.  loadAllRendezVous()
  }

  loadAllRendezVous(): void {
    this.rendezVousService.getAllRendezVous().subscribe((data: RendezVous[])  => {
      this.rendezVousList = data;
      // console.log(this.rendezVousList);
    });
  }
  acceptRendezVous(rendezVousId: number): void {
    this.rendezVousService.acceptRendezVous(rendezVousId).subscribe(() => {
      this.rendezVousList = this.rendezVousList.filter(r => r.id !== rendezVousId);
      this.loadAllRendezVous();
    });
  }


  cancelRendezVous(rendezVousId: number): void {
    this.rendezVousService.cancelRendezVous(rendezVousId).subscribe(() => {
      this.rendezVousList = this.rendezVousList.filter(r => r.id !== rendezVousId);
      this.loadAllRendezVous();
    });
  }
}
