import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { Subject } from 'rxjs';
import { UserDTO } from '../entities/user';
import { Movie, MovieShort } from '../entities/movie';

@Injectable({
  providedIn: 'root',
})

export class UserService {


  public isMovieAddedToWatchlist :Subject<boolean> = new Subject<boolean>();

  constructor(private restService: RestService) { }

  public isMovieAddedToUserWatchlist(user: UserDTO, movie: Movie) {
    this.restService.getMovieFromUserWatchlist(user, movie).subscribe((movieData) => {
      if(movieData !== null) {
        this.isMovieAddedToWatchlist.next(true);
      } else {
        this.isMovieAddedToWatchlist.next(false);
      }
    })
  }


}
