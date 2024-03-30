import { WatchlistElement } from './../entities/WatchlistElement';
import { Observable, Subject } from 'rxjs';
import { UserDTO, UserLoginDTO, UserRegisterDTO } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie, MovieShort } from '../entities/movie';
import RatingResponse from '../entities/RatingResponse';
import { MovieStatistics } from '../entities/MovieStatistics';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/login`],
    ['REGISTER_USER', `${this.API}/auth/register`],
    ['GET_ALL_MOVIES', `${this.API}/movie/movies`],
    ['GET_ALL_MOVIES_SHORT_DATA', `${this.API}/movie/movies/short`],
    ['GET_MOVIE_BY_ID', `${this.API}/movie/movies/:movieId`],
    ['ADD_MOVIE_TO_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['REMOVE_MOVIE_FROM_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['GET_MOVIE_FROM_WATCHLIST', `${this.API}/user/users/:userId/watchlist/:movieId`],
    ['GET_WATCHLIST_ELEMENT_BY_USER_AND_MOVIE', `${this.API}/watchlist/watchlists/:userId/movies/:movieId`],
    ['SET_WATCHLIST_ELEMENT_FINISHED', `${this.API}/watchlist/watchlists/elements/:elementId/finished`],
    ['SET_WATCHLIST_ELEMENT_NOT_FINISHED', `${this.API}/watchlist/watchlists/elements/:elementId/unfinished`],
    ['GET_WATCHLIST_ELEMENT_MOVIE_AVG_RATING', `${this.API}/watchlist/watchlists/elements/:elementId/average-rating`],
    ['GET_WATCHLIST_ELEMENT_MOVIE_STATISTICS', `${this.API}/watchlist/watchlists/elements/:elementId/statistics`]
  ]);

  constructor(private http: HttpClient) { }

  public authenticateUser(user: UserLoginDTO): Observable<UserDTO> {
    console.log(user);
    return this.http.post<UserDTO>(this.getEndpoint('AUTH_USER'), {
      username: user.username,
      password: user.password
    }, {
      headers: {

      },
      responseType: "json"
    });
  }

  public registerUser(user: UserRegisterDTO) {
    return this.http.post<UserRegisterDTO>(this.getEndpoint('REGISTER_USER'), user, {
      headers: {

      },
      responseType: "json"
    });
  }

  public getAllMovies() {
    const requestUrl = this.getEndpoint('GET_ALL_MOVIES')
    return this.http.get<Movie[]>(requestUrl, {
      headers: {

      },
      responseType: "json"
    });
  }

  public getAllMoviesShortData() {
    const requestUrl = this.getEndpoint('GET_ALL_MOVIES_SHORT_DATA')
    return this.http.get<MovieShort[]>(requestUrl, {
      headers: {

      },
      responseType: "json"
    });
  }

  public getMovieById(movieId: string | number) {
    const requestUrl = this.getEndpoint('GET_MOVIE_BY_ID')
      .replace(':movieId', typeof(movieId) === 'string' ? movieId : movieId.toString());
    return this.http.get<Movie>(requestUrl, {
      headers: {

      },
      responseType: 'json'
    });
  }

  public addMovieToWatchlist(user: UserDTO, movie: Movie) {
    const requestUrl = this.getEndpoint('ADD_MOVIE_TO_WATCHLIST')
      .replace(':userId', user.userId.toString());
    let payload = {
      userId: user.userId,
      movieId: movie.movieId
    }
    return this.http.post<WatchlistElement[]>(requestUrl, payload, {
      headers: {

      },
      responseType: 'json'
    })
  }

  public removeMovieFromWatchlist(user: UserDTO, movie: Movie) {
    const requestUrl = this.getEndpoint('REMOVE_MOVIE_FROM_WATCHLIST')
      .replace(':userId', user.userId.toString());
    let payload = {
      userId: user.userId,
      movieId: movie.movieId
    }
    return this.http.delete<WatchlistElement[]>(requestUrl, {
      body: payload,
      responseType: "json"
    })
  }

  public getWatchlistElementByUserAndMovie(user: UserDTO, movie: Movie) {
    const requestUrl = this.getEndpoint('GET_WATCHLIST_ELEMENT_BY_USER_AND_MOVIE')
      .replace(':userId', user.userId.toString())
      .replace(':movieId', movie.movieId.toString());
    let payload = {
      userId: user.userId,
      movieId: movie.movieId
    }
    return this.http.get<WatchlistElement>(requestUrl, {
      headers: {

      },
      responseType: 'json'
    })
  }

  public setWatchlistElementAsFinished(watchlistElement: WatchlistElement) {
    const requestUrl = this.getEndpoint('SET_WATCHLIST_ELEMENT_FINISHED')
      .replace(':elementId', watchlistElement.watchlistElementId.toString())
    return this.http.post<WatchlistElement>(requestUrl, {

    }, {
      headers: {

      },
      responseType: 'json'
    });
  }

  public setWatchlistElementAsNotFinished(watchlistElement: WatchlistElement) {
    const requestUrl = this.getEndpoint('SET_WATCHLIST_ELEMENT_NOT_FINISHED')
      .replace(':elementId', watchlistElement.watchlistElementId.toString());
    return this.http.post<WatchlistElement>(requestUrl, {

    }, {
      headers: {

      },
      responseType: 'json'
    });
  }

  public getWatchlistElementReviewedEntityAverateRating(watchlistElement: WatchlistElement) {
    const requestUrl = this.getEndpoint('GET_WATCHLIST_ELEMENT_MOVIE_AVG_RATING')
      .replace(':elementId', watchlistElement.watchlistElementId.toString());
    return this.http.get<RatingResponse>(requestUrl, {
      headers: {

      },
      responseType: 'json'
    })
  }

  public getWatchlistElementReviewedEntityStatistics(watchlistElement: WatchlistElement) {
    const requestUrl = this.getEndpoint('GET_WATCHLIST_ELEMENT_MOVIE_STATISTICS')
      .replace(':elementId', watchlistElement.watchlistElementId.toString());
    return this.http.get<MovieStatistics>(requestUrl, {
      headers: {

      },
      responseType: 'json'
    })
  }

  public getMovieFromUserWatchlist(user: UserDTO, movie: Movie) {
    const requestUrl = this.getEndpoint('GET_MOVIE_FROM_WATCHLIST')
      .replace(':userId', user.userId.toString())
      .replace(':movieId', movie.movieId.toString());
    return this.http.get<MovieShort>(requestUrl, {
      headers: {

      },
      responseType: "json"
    })
  }

  private getEndpoint(endpointKey: string): string {
    return this.ENDPOINTS.get(endpointKey)!;
  }
}
