import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import routeConfig from './app/routes'
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import AuthenticationInterceptor from './app/auth/Interceptors/authenticationInterceptor';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routeConfig),
    {
      provide:  HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true
    }, provideAnimationsAsync()
  ]
}).catch((err) => console.error(err));
