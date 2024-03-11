import { Observable } from 'rxjs';
import { UserDTO, UserLoginDTO, UserRegisterDTO } from './../entities/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private API: string = 'http://localhost:8080/api';
  private token: string = "Basic YWRtaW46YWRtaW4=";

  private ENDPOINTS = new Map<string, string>([
    ['AUTH_USER', `${this.API}/auth/user`],
    ['REGISTER_USER', `${this.API}/auth/register`]
  ]);

  constructor(private http: HttpClient) { }

  public authenticateUser(user: UserLoginDTO): Observable<UserDTO> {

    return this.http.post<UserDTO>(this.getEndpoint('AUTH_USER'), {
      user
    }, {
      headers: {
        Authorization: this.token,
      },
      responseType: "json"
    });
  }

  public registerUser(user: UserRegisterDTO) {
    return this.http.post<UserRegisterDTO>(this.getEndpoint('REGISTER_USER'), user, {
      headers: {
        Authorization: this.token
      },
      responseType: "json"
    });
  }

  private getEndpoint(endpointKey: string): string {
    return this.ENDPOINTS.get(endpointKey)!;
  }
}
