import { Subject } from 'rxjs';
import { WatchlistService } from './../../service/watchlist.service';
import { routes } from './../../app.routes';
import { MovieShort } from './../../entities/movie';
import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { OnInit } from '@angular/core';
import { MovieService } from '../../service/movie.service';
import { UserDTO } from '../../entities/user';

@Component({
  selector: 'app-movie-tile',
  standalone: true,
  imports: [RouterModule],
  providers: [WatchlistService, MovieService],
  template: `
    <section class="movie-container">
      <h5 class="movie-heading">{{movie.title}}</h5>
      <img class="movie-cover" src={{movie.cover}} (click)="navigateToMovieDetails(movie)">
      <ul class="movie-details-container">
        <li class="movie-details-item">Released: {{movie.releaseDate.substring(0, movie.releaseDate.indexOf("("))}}</li>
        <li class="movie-details-item">Production: {{movie.countryOfOrigin}}</li>
      </ul>
    </section>
  `,
  styleUrl: './movie-tile.component.css'
})
export class MovieTileComponent{
  @Input() movie!: MovieShort;

  user: UserDTO;

  constructor(private router: Router, private movieService: MovieService, private watchlistService: WatchlistService){
    this.user = JSON.parse(sessionStorage['user']);
  }

  protected navigateToMovieDetails(ms: MovieShort) {
    this.movieService.getMovieById(ms.movieId.toString())?.subscribe((movie) => {
      this.watchlistService.getWatchlistElementByMovieAndUser(this.user, movie).subscribe((element) => {
        if(element) {
          this.movieService.getMovieFromWatchlistElementStatistics(element).subscribe((statistics) => {
            this.router.navigate(["movie/", ms.movieId], {state: {
              movie: movie,
              watchlistElement: element,
              statistics: statistics
            }});
          });
        } else {
          this.router.navigate(["movie/", ms.movieId], {state: {
            movie: movie,
            watchlistElement: element,
            statistics: undefined
          }});
        }
      });
    });
  }

}
