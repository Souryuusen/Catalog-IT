import { Observable } from 'rxjs';
import { UserDTO, UserLoginDTO, UserRegisterDTO } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';


  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/login`],
    ['REGISTER_USER', `${this.API}/auth/register`]
  ]);

  constructor(private http: HttpClient) { }

  public authenticateUser(user: UserLoginDTO): Observable<UserDTO> {
    console.log(user);
    return this.http.post<UserDTO>(this.getEndpoint('AUTH_USER'), {
      username: user.username,
      password: user.password
    }, {
      headers: {

      },
      responseType: "json"
    });
  }

  public registerUser(user: UserRegisterDTO) {
    return this.http.post<UserRegisterDTO>(this.getEndpoint('REGISTER_USER'), user, {
      headers: {

      },
      responseType: "json"
    });
  }

  private getEndpoint(endpointKey: string): string {
    return this.ENDPOINTS.get(endpointKey)!;
  }
}
