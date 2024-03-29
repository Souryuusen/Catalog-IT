import { WatchlistElement } from './../entities/WatchlistElement';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, of } from 'rxjs';
import { RestService } from './rest.service';
import { UserDTO } from '../entities/user';
import { Movie } from '../entities/movie';

@Injectable({
  providedIn: 'root'
})

export class WatchlistService {

  private watchlistSubject = new BehaviorSubject<WatchlistElement[]>([]);
  public watchlist$ = this.watchlistSubject.asObservable();

  private watchlistElementSubject = new BehaviorSubject<WatchlistElement|undefined>(undefined);
  public watchlistElement$ = this.watchlistElementSubject.asObservable()

  constructor(private restService: RestService) {}

  public addMovieToWatchlist(user: UserDTO, movie: Movie) {
    this.restService.addMovieToWatchlist(user, movie).subscribe((userWatchlist) => {
      this.watchlistSubject.next(userWatchlist);
      this.watchlistElementSubject.next(userWatchlist.filter((element) => element.reviewedEntityId === movie.movieId)[0])
    });
  }

  public removeMovieFromWatchlist(user: UserDTO, movie: Movie) {
    this.restService.removeMovieFromWatchlist(user, movie).subscribe((userWatchlist) => {
      this.watchlistSubject.next(userWatchlist);
      this.watchlistElementSubject.next(undefined)
    });
  }

  public getWatchlistElementByMovieAndUser(user: UserDTO, movie: Movie) {
    return this.restService.getWatchlistElementByUserAndMovie(user, movie).pipe(map((watchlistElement) => {
      // this.watchlistElementSubject.next(watchlistElement)
      return watchlistElement;
    }), catchError((error) => {
      // this.watchlistElementSubject.next(undefined)
      return of(undefined);
    }));
  }
}
