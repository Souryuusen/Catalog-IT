import { WatchlistService } from './../../service/watchlist.service';
import { RestService } from './../../service/rest.service';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { UserDTO } from '../../entities/user';
import { Movie } from '../../entities/movie';
import { CommonModule } from '@angular/common';
import { UserService } from '../../service/user.service';
import { WatchlistElement } from '../../entities/WatchlistElement';

@Component({
  selector: 'app-user-actions',
  standalone: true,
  imports: [CommonModule],
  providers: [RestService, UserService, WatchlistService],
  templateUrl: './user-actions.component.html',
  styleUrl: './user-actions.component.css'
})
export class UserActionsComponent implements OnInit, OnDestroy{

  @Input() user!: UserDTO;
  @Input() movie!: Movie;
  @Input() watchlistElement!: WatchlistElement | undefined;

  userWatchlist: WatchlistElement[] = [];

  isOnUserWatchlist: boolean = false;
  isLoaded: boolean = false;

  constructor(protected restService: RestService, protected userService: UserService,
              protected watchlistService: WatchlistService) {

  }

  ngOnInit(): void {
    // if(this.watchlistElement !== undefined) {
    //   this.isOnUserWatchlist = true;
    // }
    this.watchlistService.watchlistElement$.subscribe((element) => {
      this.watchlistElement = element;
      this.isOnUserWatchlist = this.watchlistElement === undefined;
      console.log(this.watchlistElement);
    })
  }

  ngOnDestroy(): void {

  }

  protected addToWatchlist(): void {
    this.watchlistService.addMovieToWatchlist(this.user, this.movie);
  }

  protected removeFromWatchlist(): void {
    this.watchlistService.removeMovieFromWatchlist(this.user, this.movie)
  }

}
