import { Injectable } from '@angular/core';
import { Director } from '../entities/director';

@Injectable({
  providedIn: 'root'
})
export class DirectorService {

  constructor() { }

  public joinDirectorsNames(directors: Director[], delimeter: string = ", "): string {
    let result = "";

    directors.forEach((value, index) => {
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
