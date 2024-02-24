import { Injectable } from '@angular/core';
import { Movie } from '../entities/movie';
import { Genre } from '../entities/genre';
import { Tag } from '../entities/tag';

@Injectable({
  providedIn: 'root'
})

export class MovieService {

  constructor() { }

  scrapeMovieByTitle(title: string): Movie | undefined {
    return undefined;
  }

  fetchAllMovies(): Movie[] | undefined {
    return undefined;
  }

  fetchMovieById(id: number): Movie | undefined {
    return undefined;
  }

  fetchMovieByTitle(title: string): Movie[] | undefined {
    return undefined;
  }

  saveNewMovie(movie: Movie): boolean {
    return false;
  }

  fetchMoviesByGenre(genre: Genre): Movie | undefined {
    return undefined;
  }

  fetchMoviesByTag(tag: Tag): Movie | undefined {
    return undefined;
  }

}
