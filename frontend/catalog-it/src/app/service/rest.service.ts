import { Observable, Subject } from 'rxjs';
import { UserDTO, UserLoginDTO, UserRegisterDTO } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie, MovieShort } from '../entities/movie';
import { WatchlistElement } from '../entities/WatchlistElement';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/login`],
    ['REGISTER_USER', `${this.API}/auth/register`],
    ['ADD_MOVIE_TO_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['REMOVE_MOVIE_FROM_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['GET_MOVIE_FROM_WATCHLIST', `${this.API}/user/users/:userId/watchlist/:movieId`],
    ['GET_WATCHLIST_ELEMENT_BY_USER_AND_MOVIE', `${this.API}/watchlist/watchlists/:userId/movies/:movieId`]
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
