import { CommonModule } from '@angular/common';
import { Movie } from './../../entities/movie';
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
  movieList: Movie[] = [];
  protected movieService: MovieService;

  constructor(movieService: MovieService) {
    this.movieService = movieService;
    this.obtainAllMovies();

  }

  obtainAllMovies() {
    this.movieService.fetchAllMovies()?.subscribe(movies => {
      this.movieList = movies;
      this.movieList?.forEach(movie => {
        console.log('Movie\r\n' + movie.title)
      });
      this.loaded = true;
    });
  }

}
