import { Component, Input } from '@angular/core';
import { MovieShort } from '../../entities/movie';

@Component({
  selector: 'app-movie-tile',
  standalone: true,
  imports: [],
  template: `
    <section class="movie-container">
      <h5 class="movie-heading">{{movie.title}}</h5>
      <img class="movie-cover" src={{movie.cover}}>
      <ul class="movie-details-container">
        <li class="movie-details-item">Released: {{movie.releaseDate.substring(0, movie.releaseDate.indexOf("("))}}</li>
        <li class="movie-details-item">Production: {{movie.countryOfOrigin}}</li>
      </ul>
    </section>
  `,
  styleUrl: './movie-tile.component.css'
})
export class MovieTileComponent {
  @Input() movie!: MovieShort;
}
