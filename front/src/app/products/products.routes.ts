import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import { AuthGuard } from "../auth/auth.guard";

export const PRODUCTS_ROUTES: Routes = [
  {
    path: "",
    redirectTo: "list",
    pathMatch: "full",
  },
  {
    path: "list",
    component: ProductListComponent,
    canActivate: [AuthGuard],
  },
  { path: "**", redirectTo: "list" },
];
