import { Movie } from './../../entities/movie';
import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieService } from '../../service/movie.service';
import { MovieStatistics } from '../../entities/MovieStatistics';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-movie-statistics',
  standalone: true,
  imports: [CommonModule],
  providers: [MovieService],
  templateUrl: './movie-statistics.component.html',
  styleUrl: './movie-statistics.component.css'
})
export class MovieStatisticsComponent implements OnInit, OnDestroy{

  @Input() initialMovieStatistic!: MovieStatistics | undefined;

  protected movieStatistics$: Observable<MovieStatistics | undefined>;

  constructor(private movieService: MovieService) {
    this.movieStatistics$ = this.movieService.movieStatistics$;
  }

  ngOnInit(): void {
    this.movieService.emitStatistics(this.initialMovieStatistic);
    this.movieStatistics$.subscribe((statistics) => {
      this.initialMovieStatistic = statistics;
    })
  }

  ngOnDestroy(): void {

  }




}
