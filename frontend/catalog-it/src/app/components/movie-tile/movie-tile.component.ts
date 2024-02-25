import { routes } from './../../app.routes';
import { MovieShort } from './../../entities/movie';
import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-movie-tile',
  standalone: true,
  imports: [RouterModule],
  template: `
    <section class="movie-container">
      <h5 class="movie-heading">{{movie.title}}</h5>
      <img class="movie-cover" src={{movie.cover}} (click)="navigateToMovieDetails(movie)">
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

  constructor(private router: Router){

  }

  protected navigateToMovieDetails(ms: MovieShort) {
    this.router.navigate(["movie/", ms.movieId]);
  }

}
