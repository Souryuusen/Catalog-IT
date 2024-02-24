import { Component } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { HomeComponent } from './components/home/home.component'
import { RouterModule } from '@angular/router'
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HomeComponent, RouterModule, NavigationBarComponent],
  template:
  `
    <main>
        <header class="brand-name">
          <img class="brand-logo" src="./assets/logo.svg" alt="Logo" aria-hidden="true">
        </header>
        <app-navigation-bar></app-navigation-bar>
        <section class="content">
        <router-outlet></router-outlet>
        </section>
    </main>
  `,
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'catalog-it'
}
