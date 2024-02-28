import { Injectable } from '@angular/core';
import { Genre } from '../entities/genre';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  constructor() { }

  public joinGenresNames(genres: Genre[], delimeter: string = ", "): string {
    let result = "";

    genres.forEach((value, index) => {
      result += value.name + delimeter;
    });
    if(result.startsWith(delimeter)) {
      result = result.substring(delimeter.length);
    }
    if(result.endsWith(delimeter)) {
      result = result.substring(0, result.length - delimeter.length);
    }
    return result;
  }
}
