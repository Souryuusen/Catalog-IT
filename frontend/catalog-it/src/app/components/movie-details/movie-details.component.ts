import { TagService } from './../../service/tag.service';
import { WriterService } from './../../service/writer.service';
import { DirectorService } from './../../service/director.service';
import { GenreService } from './../../service/genre.service';
import { CommonModule } from '@angular/common';
import { MovieService } from './../../service/movie.service';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Movie } from '../../entities/movie';
import { OnInit } from '@angular/core';
import { ActorService } from '../../service/actor.service';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  providers: [
    MovieService, GenreService, WriterService, DirectorService,
    ActorService
  ],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css'
})

export class MovieDetailsComponent implements OnInit {
  private movieId: string;

  protected movie: Movie | undefined = undefined;
  protected loaded: boolean = false;

  protected genreString: string = "";
  protected directorString: string = "";
  protected writerString: string = "";
  protected actorString: string = "";
  protected tagString: string = "";

  constructor(private movieService: MovieService, private genreService: GenreService,
                private directorService: DirectorService, private writerService: WriterService,
                  private actorService: ActorService, private tagService: TagService, private route: ActivatedRoute) {
    this.movieId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.fetchMovieById(this.movieId)
  }

  private fetchMovieById(id: string) {
    this.movieService.fetchMovieById(id)?.subscribe(movie => {
      this.movie = movie;
      this.genreString = this.genreService.joinGenresNames(this.movie.genres);
      this.directorString = this.directorService.joinDirectorsNames(this.movie.directors);
      this.writerString = this.writerService.joinWritersNames(this.movie.writers);
      this.actorString = this.actorService.joinActorsNames(this.movie.stars);
      this.tagString = this.tagService.joinTagsNames(this.movie.keywords);
      this.loaded = true;
    });
  }

}
