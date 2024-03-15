import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { OnInit } from '@angular/core';
import { UserDTO } from '../../entities/user';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [RouterModule, CommonModule],
  template: `
    <section class="navigation-bar">
      <div clas="topnav">
        <a routerLink="/movies" class="topnav-item">Browse</a>
        <a routerLink="/search" class="topnav-item">Find</a>
        <a *ngIf="this.user.userId === 0" routerLink="/register" class="topnav-item">Register</a>
        <a *ngIf="this.user.userId === 0" routerLink="/login" class="topnav-item">Log in</a>
        <a *ngIf="this.user.userId !== 0" routerLink="/logout" class="topnav-item">Log out</a>
      </div>
    </section>
  `,
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

  protected user: UserDTO = {username: "", email: "", userId: 0};


  constructor(private router: Router) {

  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if(event.constructor.name == "NavigationEnd") {
        sessionStorage['user'] ? this.user = sessionStorage['user'] : this.user = {username: "", email: "", userId: 0};
      }
    });
  }

}
