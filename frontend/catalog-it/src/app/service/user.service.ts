import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { Subject } from 'rxjs';
import { UserDTO } from '../entities/user';
import { Movie, MovieShort } from '../entities/movie';

@Injectable({
  providedIn: 'root',
})

export class UserService {

  constructor(private restService: RestService) { }

}
