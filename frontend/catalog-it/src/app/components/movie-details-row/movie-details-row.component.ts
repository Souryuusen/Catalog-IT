import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-movie-details-row',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './movie-details-row.component.html',
  styleUrl: './movie-details-row.component.css'
})

export class MovieDetailsRowComponent {

  @Input() message!: string;
  @Input() value!: string;
  @Input() visible!: boolean;

}
