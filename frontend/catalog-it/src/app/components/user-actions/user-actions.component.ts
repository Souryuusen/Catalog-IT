import { RestService } from './../../service/rest.service';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { UserDTO } from '../../entities/user';
import { Movie } from '../../entities/movie';
import { CommonModule } from '@angular/common';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-actions',
  standalone: true,
  imports: [CommonModule],
  providers: [RestService, UserService],
  templateUrl: './user-actions.component.html',
  styleUrl: './user-actions.component.css'
})
export class UserActionsComponent implements OnInit, OnDestroy{

  @Input() user!: UserDTO;
  @Input() movie!: Movie;

  isOnUserWatchlist: boolean = false;
  isLoaded: boolean = false;

  constructor(protected restService: RestService, protected userService: UserService) {

  }

  ngOnInit(): void {
      this.userService.isMovieAddedToUserWatchlist(this.user, this.movie);
      this.userService.isMovieAddedToWatchlist.subscribe((isAdded) => {
        this.isOnUserWatchlist = isAdded;
      });
  }

  ngOnDestroy(): void {
    this.userService.isMovieAddedToWatchlist.unsubscribe()
  }

  protected addToWatchlist(): void {
    this.restService.addMovieToWatchlist(this.user, this.movie).subscribe((addedMovie) => {
      if(addedMovie.movieId == this.movie.movieId) {
        this.userService.isMovieAddedToUserWatchlist(this.user, this.movie);
      }
    });
  }

  protected removeFromWatchlist(): void {
    this.restService.removeMovieToWatchlist(this.user, this.movie).subscribe((data) => {
      if(data.movieId == this.movie.movieId) {
        this.userService.isMovieAddedToUserWatchlist(this.user, this.movie);
      }
    });
  }

}
