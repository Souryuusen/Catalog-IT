import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../Services/auth.service";

@Injectable()
export default class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.authService.getAuthorizationToken();
    let request: HttpRequest<any> | undefined = undefined;
    if(this.authService.isInternalRequest(req)){
      request = req.clone({
        setHeaders: {
          Authorization: authToken
        }
      });

    }

    return next.handle(request ? request : req);
  }

}
