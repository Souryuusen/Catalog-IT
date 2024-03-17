import { Observable, Subject } from 'rxjs';
import { UserDTO, UserLoginDTO, UserRegisterDTO } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie, MovieShort } from '../entities/movie';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/login`],
    ['REGISTER_USER', `${this.API}/auth/register`],
    ['ADD_MOVIE_TO_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['REMOVE_MOVIE_TO_WATCHLIST', `${this.API}/user/users/:userId/watchlist`],
    ['GET_MOVIE_FROM_WATCHLIST', `${this.API}/user/users/:userId/watchlist/:movieId`]
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
    return this.http.post<MovieShort>(requestUrl, payload, {
      headers: {

      },
      responseType: 'json'
    })
  }

  public removeMovieToWatchlist(user: UserDTO, movie: Movie) {
    const requestUrl = this.getEndpoint('REMOVE_MOVIE_TO_WATCHLIST')
      .replace(':userId', user.userId.toString());
    let payload = {
      userId: user.userId,
      movieId: movie.movieId
    }
    return this.http.delete<MovieShort>(requestUrl, {
      body: payload,
      responseType: "json"
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
