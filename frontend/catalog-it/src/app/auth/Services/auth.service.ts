import { HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private INTERNAL_SERVER_URL = "http://localhost:8080/";
  private token: string = "Basic YWRtaW46YWRtaW4=";

  constructor() {

  }

  public getAuthorizationToken(): string {
    return this.token;
  }

  public isInternalRequest(request: HttpRequest<any>): boolean {
    return request.urlWithParams.startsWith(this.INTERNAL_SERVER_URL);
  }

  public isUserLoggedIn(): boolean {
    return sessionStorage['user'] ? true : false;
  }
}
