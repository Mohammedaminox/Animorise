import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-unauthorized',
  template: `
    <div class="container mx-auto p-6 bg-white rounded-xl shadow-md max-w-md text-center">
      <h2 class="text-xl font-semibold text-gray-900 mb-4">Unauthorized</h2>
      <p>{{ message }}</p>
    </div>
  `,
  standalone: true,
  styles: []
})
export class UnauthorizedComponent {
  message: string;

  constructor(private route: ActivatedRoute) {
    this.message = this.route.snapshot.queryParams['message'] || 'You are not authorized to view this page.';
  }
}
