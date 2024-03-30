import { WatchlistElement } from './../../entities/WatchlistElement';
import { Subject } from 'rxjs';
import { Movie } from './../../entities/movie';
import { TagService } from './../../service/tag.service';
import { WriterService } from './../../service/writer.service';
import { DirectorService } from './../../service/director.service';
import { GenreService } from './../../service/genre.service';
import { CommonModule } from '@angular/common';
import { MovieService } from './../../service/movie.service';
import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { OnInit } from '@angular/core';
import { ActorService } from '../../service/actor.service';
import MovieData from '../../entities/movie-data';
import { MovieDetailsRowComponent } from '../movie-details-row/movie-details-row.component';
import { UserActionsComponent } from '../user-actions/user-actions.component';
import { UserDTO } from '../../entities/user';
import { AuthService } from '../../auth/Services/auth.service';
import { WatchlistService } from '../../service/watchlist.service';
import { RestService } from '../../service/rest.service';
import { MovieStatisticsComponent } from '../movie-statistics/movie-statistics.component';
import { MovieStatistics } from '../../entities/MovieStatistics';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule, RouterModule, MovieDetailsRowComponent, UserActionsComponent, MovieStatisticsComponent],
  providers: [
    MovieService, GenreService, WriterService, DirectorService,
    ActorService, AuthService, WatchlistService, RestService
  ],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css'
})

export class MovieDetailsComponent implements OnInit {
  private movieId: string;

  private currentCoverIndex: number = 0;
  protected currentCover: string = "";

  protected movie: Movie | undefined = undefined;
  protected user: UserDTO = {username: "" , email: "", userId: 0};
  protected watchlistElement: WatchlistElement | undefined = undefined;
  protected movieStatistics: MovieStatistics | undefined = undefined;

  protected loaded: boolean = false;
  protected userIsLogged: boolean = false;

  protected genreString: string = "";
  protected directorString: string = "";
  protected writerString: string = "";
  protected actorString: string = "";
  protected tagString: string = "";

  protected movieData: MovieData[] = [];

  constructor(private movieService: MovieService, private genreService: GenreService,
                private directorService: DirectorService, private writerService: WriterService,
                  private actorService: ActorService, private tagService: TagService, private authService: AuthService,
                    private watchlistService: WatchlistService, private route: ActivatedRoute, private router: Router) {
    this.loaded = true;
    this.movieId = this.route.snapshot.params['id'];
    const state = this.router.getCurrentNavigation()?.extras.state;
    if(state) {
      this.movie = state['movie'];
      this.watchlistElement = state['watchlistElement'];
      this.movieStatistics = state['statistics'];
    }
  }

  ngOnInit(): void {
    this.fetchData();
    this.verifyUser();
  }

  private fetchData() {
    if(this.watchlistElement) {
      this.watchlistService.setWatchlistElement(this.watchlistElement);
    }
    if(this.movie) {
      this.genreString = this.genreService.joinGenresNames(this.movie.genres);
      this.directorString = this.directorService.joinDirectorsNames(this.movie.directors);
      this.writerString = this.writerService.joinWritersNames(this.movie.writers);
      this.actorString = this.actorService.joinActorsNames(this.movie.stars);
      this.tagString = this.tagService.joinTagsNames(this.movie.keywords);

      if(this.movie.originalTitle !== "") {
        this.movieData.push(new MovieData("Also known as:", this.movie.originalTitle));
      }
      this.movieData.push(new MovieData(`Genre${this.movie.genres.length > 1 ? "s:" : ":"}`, this.genreString));
      this.movieData.push(new MovieData(`Director${this.movie.directors.length > 1 ? "s:" : ":"}`, this.directorString));
      this.movieData.push(new MovieData(`Writer${this.movie.writers.length > 1 ? "s:" : ":"}`, this.writerString));
      this.movieData.push(new MovieData(`Actor${this.movie.stars.length > 1 ? "s:" : ":"}`, this.actorString));
      this.movieData.push(new MovieData(`Keyword${this.movie.keywords.length > 1 ? "s:" : ":"}`, this.tagString));

      this.currentCover = this.movie.covers[this.currentCoverIndex];
    }
  }

  /**
   * @todo Create Logic For User Authentication and storing token
   *
   */
  private verifyUser() {
    this.userIsLogged = this.authService.isUserLoggedIn();
    if(this.userIsLogged) {
      this.user = JSON.parse(sessionStorage['user']);
    }
  }

  protected changeCoverIndex(changeValue: number) {
    if(this.movie !== undefined) {
      if(changeValue > 0) {
        if(this.currentCoverIndex + changeValue <= this.movie.covers.length - 1) {
          this.currentCoverIndex += changeValue;
        } else {
          this.currentCoverIndex = this.movie.covers.length - 1;
        }
      } else if(changeValue < 0) {
        if(this.currentCoverIndex + changeValue >= 0) {
          this.currentCoverIndex += changeValue;
        } else {
          this.currentCoverIndex = 0;
        }
      }
      this.currentCover = this.movie.covers[this.currentCoverIndex];
    }
  }

  protected canChangeCoverIndex(changeValue: number): boolean {
    if(this.movie !== undefined) {
      if(changeValue < 0) {
        return this.currentCoverIndex + changeValue >= 0;
      } else if(changeValue > 0) {
        return this.currentCoverIndex + changeValue <= this.movie.covers.length - 1;
      }
    }
    return false;
  }

}
