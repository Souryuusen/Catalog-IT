import { MovieStatistics } from '../entities/MovieStatistics';

import { Injectable } from '@angular/core';
import { Movie, MovieShort } from '../entities/movie';
import { Genre } from '../entities/genre';
import { Tag } from '../entities/tag';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, map, of } from 'rxjs';
import { RestService } from './rest.service';
import { WatchlistElement } from '../entities/WatchlistElement';

@Injectable({
  providedIn: 'root'
})

export class MovieService {

  private movieStatisticsSubject = new BehaviorSubject<MovieStatistics | undefined>(undefined);
  public movieStatistics$ = this.movieStatisticsSubject.asObservable();

  constructor(private http: HttpClient, private restService: RestService) { }

  public getAllMovies(): Observable<Movie[]> {
    return this.restService.getAllMovies();
  }

  public getAllMoviesShortData(): Observable<MovieShort[]> {
    return this.restService.getAllMoviesShortData();
  }

  public getMovieById(id: string): Observable<Movie> {
    return this.restService.getMovieById(id);
  }

  public getMovieFromWatchlistElementStatistics(element: WatchlistElement) {
    return this.restService.getWatchlistElementReviewedEntityStatistics(element).pipe(map((statistic) => {
      this.movieStatisticsSubject.next(statistic)
      return statistic;
    }), catchError((error) => {
      this.movieStatisticsSubject.next(undefined)
      return of(undefined);
    }));
  }

  public emitStatistics(movieStatistics: MovieStatistics | undefined) {
    this.movieStatisticsSubject.next(movieStatistics);
  }

  public getMovieByTitle(title: string): Movie[] | undefined {
    return undefined;
  }

  public saveMovie(movie: Movie): boolean {
    return false;
  }

  public getMoviesByGenre(genre: Genre): Movie | undefined {
    return undefined;
  }

  public getMoviesByTag(tag: Tag): Movie | undefined {
    return undefined;
  }

  public searchForMovieByTitle(title: string): Movie | undefined {
    return undefined;
  }

  public scrapeMovieByTitle(title: string): Movie | undefined {
    return undefined;
  }

}
