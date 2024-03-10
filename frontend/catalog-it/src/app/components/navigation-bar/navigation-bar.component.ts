import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <section class="navigation-bar">
      <div clas="topnav">
        <a routerLink="/movies" class="topnav-item">Browse</a>
        <a routerLink="/search" class="topnav-item">Find</a>
        <a routerLink="/register" class="topnav-item">Register</a>
        <a routerLink="/login" class="topnav-item">Login</a>
      </div>
    </section>
  `,
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

}
