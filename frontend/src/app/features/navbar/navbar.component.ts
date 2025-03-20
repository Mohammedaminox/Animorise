import { Component, HostListener } from '@angular/core';
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.component.html',
  imports: [
    NgClass
  ],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  navbarClass: string = ''; // Variable to hold the dynamic navbar class

  @HostListener('window:scroll', ['$event'])
  onScroll(event: Event): void {
    const scrollPosition = window.scrollY;
    if (scrollPosition > 50) {
      this.navbarClass = 'bg-purple-900 shadow-lg'; // Add classes when scrolled down
    } else {
      this.navbarClass = ''; // Remove classes when at the top
    }
  }
}
