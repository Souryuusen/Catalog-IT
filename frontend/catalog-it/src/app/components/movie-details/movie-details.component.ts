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
    <section *ngIf="loaded">
      <h2>{{movie?.title}}</h2>
    </section>
  `,
  styleUrl: './movie-details.component.css'
})

export class MovieDetailsComponent implements OnInit {
  route: ActivatedRoute = inject(ActivatedRoute);

  private movieId: string;
  protected movie: Movie | undefined = undefined;
  protected loaded: boolean = false;

  constructor(private movieService: MovieService) {
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
