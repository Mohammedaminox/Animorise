import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  constructor(private authService: AuthService) {}
  userRole: string | null = null;


  ngOnInit(): void {
    this.userRole = this.authService.getCurrentUserRole();
  }

  logout() {
    this.authService.logout();
  }

}
