import { Routes } from "@angular/router"
import { HomeComponent } from "./components/home/home.component"
import { DetailsComponent } from "./components/details/details.component"
import { SearchComponent } from "./components/search/search.component";
import { MovieListComponent } from "./components/movie-list/movie-list.component";
import { MovieDetailsComponent } from "./components/movie-details/movie-details.component";
import { RegistrationFormComponent } from "./auth/Components/registration-form/registration-form.component";
import { LoginFormComponent } from "./auth/Components/login-form/login-form.component";

const routeConfig: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: "Home Page"
  },
  {
    path: 'details/:id',
    component: DetailsComponent,
    title: "Listing Details"
  },
  {
    path: "search",
    component: SearchComponent,
    title: "Find Movie"
  },
  {
    path: "movies",
    component: MovieListComponent,
    title: "List Of Movies"
  },
  {
    path: 'movie/:id',
    component: MovieDetailsComponent,
    title: "Movie Details"
  },
  {
    path: "register",
    component: RegistrationFormComponent,
    title: "Register New User"
  },
  {
    path: "login",
    component: LoginFormComponent,
    title: "Log in"
  }
]

export default routeConfig;
