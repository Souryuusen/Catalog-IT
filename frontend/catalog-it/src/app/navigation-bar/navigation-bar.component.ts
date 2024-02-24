import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <section class="navigation-bar">
      <div clas="topnav">
        <button>Movie List</button>
        <a>Search Movies</a>
        <div class="topnav-right">
          <a>Login</a>
        </div>
      </div>
    </section>
  `,
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

}
