import { Injectable } from '@angular/core';
import { Tag } from '../entities/tag';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor() { }

  public joinTagsNames(tags: Tag[], delimeter: string = ", "): string {
    let result = "";

    for(let i = 0; i < tags.length && i < 3; i++) {
      result += tags[i].name + delimeter;
    }

    if(result.startsWith(delimeter)) {
      result = result.substring(delimeter.length);
    }
    if(result.endsWith(delimeter)) {
      result = result.substring(0, result.length - delimeter.length);
    }
    return result;
  }

}
