import { Injectable } from '@angular/core';
import { RestService } from './rest.service';

@Injectable({
  providedIn: 'root',
})

export class UserService {

  constructor(private restService: RestService) { }

  /**
   *
   * @param username User's to authenticate username
   * @param password User's to authenticate password
   * @returns Authentication status
   */
  protected authenticateUser(username: string, password: string): boolean {
    this.restService.authenticateUser(username, password).subscribe((user) => {
      if(user.authToken !== "") {
        return true;
      } else {
        return false;
      }
    });
    return false;
  }
}
