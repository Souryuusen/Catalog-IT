import { Observable } from 'rxjs';
import { User } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';
  private token: string = "TEST_TOKEN";

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/user`],
    ['REGISTER_USER', `${this.API}/auth/register`]
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

  public registerUser(user: User) {
    return this.http.post<User>(this.getEndpoint('REGISTER_USER'), user, {
      headers: {
        Authorization: "Basic YWRtaW46YWRtaW4="
      },
      responseType: "json"
    });
  }

  private getEndpoint(endpointKey: string): string {
    return this.ENDPOINTS.get(endpointKey)!;
  }
}
