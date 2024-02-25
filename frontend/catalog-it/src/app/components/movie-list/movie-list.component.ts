import { CommonModule } from '@angular/common';
import { MovieShort } from './../../entities/movie';
import { Component, inject } from '@angular/core';
import { MovieService } from '../../service/movie.service';
import { MovieTileComponent } from '../movie-tile/movie-tile.component';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [CommonModule, MovieTileComponent],
  providers: [MovieService],
  template: `
    <section *ngIf="this.loaded" class="movie-results">
      <app-movie-tile  *ngFor="let movie of movieList" [movie]="movie"></app-movie-tile>
    </section>
  `,
  styleUrl: './movie-list.component.css'
})
export class MovieListComponent {
  loaded: boolean = false;
  movieList: MovieShort[] = [];
  protected movieService: MovieService;

  constructor(movieService: MovieService) {
    this.movieService = movieService;
    this.obtainAllMoviesShort();

  }

  private obtainAllMoviesShort() {
    this.movieService.fetchAllMoviesShort()?.subscribe(movies => {
      this.movieList = movies;
      this.loaded = true;
    });
  }

}
