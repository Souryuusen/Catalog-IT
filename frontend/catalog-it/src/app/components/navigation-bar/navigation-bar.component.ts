import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <section class="navigation-bar">
      <div clas="topnav">
        <a [routerLink]="['/movies']" class="topnav-item">Movie List</a>
        <a>Search Movies</a>
        <div class="topnav-right">
          <a class="topnav-right-item">Login</a>
        </div>
      </div>
    </section>
  `,
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

}
