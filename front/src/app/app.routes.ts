import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { ContactComponent } from "./contact/contact.component";
import { AuthComponent } from "./auth/features/login/auth.component";
import { RegisterComponent } from "./auth/features/register/register.component";

export const APP_ROUTES: Routes = [
  {
    path: "auth",
    component: AuthComponent,
  },
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES),
  },
  {
    path: "contact",
    component: ContactComponent,
  },
  {
    path: "register",
    component: RegisterComponent,
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];
