import { Injectable } from '@angular/core';
import { Movie } from '../entities/movie';
import { Genre } from '../entities/genre';
import { Tag } from '../entities/tag';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class MovieService {

  constructor(private http: HttpClient) { }

  searchForMovieByTitle(title: string): Movie | undefined {
    return undefined;
  }

  scrapeMovieByTitle(title: string): Movie | undefined {
    return undefined;
  }

  fetchAllMovies(): Observable<Movie[]> | undefined {
    var moviesURL = `http://localhost:8080/api/movies`;
    return this.http.get<Movie[]>(moviesURL, {responseType: "json"});
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
