import { Injectable } from '@angular/core';
import { Writer } from '../entities/writer';

@Injectable({
  providedIn: 'root'
})
export class WriterService {

  constructor() { }

  public joinWritersNames(writers: Writer[], delimeter: string = ", "): string {
    let result = "";

    writers.forEach((value, index) => {
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
