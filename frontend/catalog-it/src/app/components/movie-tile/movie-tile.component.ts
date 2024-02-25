import { Component, Input } from '@angular/core';
import { Movie } from '../../entities/movie';

@Component({
  selector: 'app-movie-tile',
  standalone: true,
  imports: [],
  template: `
    <section class="movie-container">
      <h2 class="movie-heading">{{movie.title}}</h2>
      <img class="movie-cover" src={{movie.covers[0]}}>
    </section>
  `,
  styleUrl: './movie-tile.component.css'
})
export class MovieTileComponent {
  @Input() movie!: Movie;
}
