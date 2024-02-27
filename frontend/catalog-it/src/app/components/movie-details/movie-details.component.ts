import { CommonModule } from '@angular/common';
import { MovieService } from './../../service/movie.service';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Movie } from '../../entities/movie';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  providers: [MovieService],
  template: `
    <section *ngIf="loaded && movie !== undefined">
      <article class="movie-details-container">
        <div class="movie-details-container-left">
          <h2 class="movie-details-heading, center-align">{{movie.title}}</h2>
          <h3 class="movie-details-heading, left-align" *ngIf='movie.originalTitle === ""'>Also known as: {{movie.originalTitle}}</h3>
        </div>
        <div class="movie-details-container-right">
          <img class="movie-cover" [src]="movie.covers[0]">
        </div>

      </article>


    </section>
  `,
  styleUrl: './movie-details.component.css'
})

export class MovieDetailsComponent implements OnInit {
  private movieId: string;
  protected movie: Movie | undefined = undefined;
  protected loaded: boolean = false;

  constructor(private movieService: MovieService, private route: ActivatedRoute) {
    this.movieId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.fetchMovieById(this.movieId)
  }

  private fetchMovieById(id: string) {
    this.movieService.fetchMovieById(id)?.subscribe(movie => {
      this.movie = movie;
      this.loaded = true;
    });
  }

}
