import { Routes } from "@angular/router"
import { HomeComponent } from "./home/home.component"
import { DetailsComponent } from "./details/details.component"
import { SearchComponent } from "./search/search.component";

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
}
]

export default routeConfig;
