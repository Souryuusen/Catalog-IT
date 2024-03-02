import { Observable } from 'rxjs';
import { User } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'localhost:8080/api/';
  private token: string = "TEST_TOKEN";

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER',`${this.API}/auth/user`]
  ]);

  constructor(private http: HttpClient) { }

  public authenticateUser(authUsername: string, authPassword: string): Observable<User> {

    return this.http.post<User>(this.getEndpoint('AUTH_USER'), {
      token: this.token,
      username: authUsername,
      password: authPassword
    }, {
      headers: {

      },
      responseType: "json"
    });
  }

  private getEndpoint(endpointKey: string): string {
    return this.ENDPOINTS.get(endpointKey)!;
  }
}
