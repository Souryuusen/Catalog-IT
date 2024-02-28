import { Injectable } from '@angular/core';
import { Actor } from '../entities/actor';

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  constructor() { }

  public joinActorsNames(actors: Actor[], delimeter: string = ", "): string {
    let result = "";

    actors.forEach((value, index) => {
      result += value + delimeter;
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
