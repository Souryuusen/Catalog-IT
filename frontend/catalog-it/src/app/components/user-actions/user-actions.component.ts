import { Component, Input } from '@angular/core';
import { User } from '../../entities/user';
import { Movie } from '../../entities/movie';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-actions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-actions.component.html',
  styleUrl: './user-actions.component.css'
})
export class UserActionsComponent {

  @Input() user!: User;
  @Input() movie!: Movie;

}
