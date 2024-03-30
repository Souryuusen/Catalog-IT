import { WatchlistService } from './../../service/watchlist.service';
import { RestService } from './../../service/rest.service';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { UserDTO } from '../../entities/user';
import { Movie } from '../../entities/movie';
import { CommonModule } from '@angular/common';
import { UserService } from '../../service/user.service';
import { WatchlistElement } from '../../entities/WatchlistElement';
import { Observable, startWith, tap } from 'rxjs';

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
  currentWatchlistElement$: Observable<WatchlistElement | undefined>;

  isOnUserWatchlist: boolean = false;
  isLoaded: boolean = false;

  constructor(protected restService: RestService, protected userService: UserService,
              protected watchlistService: WatchlistService) {
    this.isLoaded = true;
    this.currentWatchlistElement$ = this.watchlistService.watchlistElement$
      // this.currentWatchlistElement$ = this.watchlistService.watchlistElement$.pipe(
      //   tap(value => console.log('Current Watchlist Element:', value))
      // );
      // console.log(this.watchlistElement)
  }

  ngOnInit(): void {
    this.watchlistService.setWatchlistElement(this.watchlistElement);
    this.currentWatchlistElement$.subscribe((element) => {
      this.watchlistElement = element;
    });
  }

  ngOnDestroy(): void {

  }

  protected addToWatchlist(): void {
    this.watchlistService.addMovieToWatchlist(this.user, this.movie);
  }

  protected removeFromWatchlist(): void {
    this.watchlistService.removeMovieFromWatchlist(this.user, this.movie)
  }

  protected setAsFinished(): void {
    if(this.watchlistElement) {
      this.watchlistService.setWatchlistElementAsFinished(this.watchlistElement);
    }
  }

  protected setAsNotFinished(): void {
    if(this.watchlistElement) {
      this.watchlistService.setWatchlistElementAsNotFinished(this.watchlistElement);
    }
  }

  protected addNewReview(): void {

  }

}
