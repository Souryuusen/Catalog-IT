import { CommonModule } from '@angular/common';
import { MovieShort } from './../../entities/movie';
import { Component, inject } from '@angular/core';
import { MovieService } from '../../service/movie.service';
import { MovieTileComponent } from '../movie-tile/movie-tile.component';
import { OnInit } from '@angular/core';
import { RestService } from '../../service/rest.service';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [CommonModule, MovieTileComponent],
  providers: [MovieService, RestService],
  template: `
    <section *ngIf="this.loaded" class="movie-results">
      <app-movie-tile  *ngFor="let movie of movieList" [movie]="movie"></app-movie-tile>
    </section>
  `,
  styleUrl: './movie-list.component.css'
})

export class MovieListComponent implements OnInit{
  loaded: boolean = false;
  movieList: MovieShort[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.obtainAllMoviesShort();
  }

  private obtainAllMoviesShort() {
    this.movieService.getAllMoviesShortData().subscribe((movies) => {
      this.movieList = movies;
      this.loaded = true;
    });
  }

}
