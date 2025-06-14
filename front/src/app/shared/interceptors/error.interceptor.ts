import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse,
} from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError } from "rxjs/operators";
import { throwError } from "rxjs";
import { MessageService } from "primeng/api";
import { Router } from "@angular/router";
import { ApiError } from "../interfaces/api.interface";

export const errorInterceptor: HttpInterceptorFn = (
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) => {
  const messageService = inject(MessageService);
  const router = inject(Router);

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = "Une erreur est survenue";
      const apiError = error.error as ApiError;

      if (error.status === 0) {
        errorMessage = "Impossible de se connecter au serveur";
      } else if (error.status === 401) {
        errorMessage = "Session expirée, veuillez vous reconnecter";
        localStorage.removeItem("token");
        router.navigate(["/auth/login"]);
      } else if (error.status === 403) {
        errorMessage = "Accès non autorisé";
      } else if (error.status === 404) {
        errorMessage = "Ressource non trouvée";
      } else if (error.status === 500) {
        errorMessage = "Erreur serveur";
      } else if (apiError?.message) {
        errorMessage = apiError.message;
      }

      messageService.add({
        severity: "error",
        summary: "Erreur",
        detail: errorMessage,
        life: 5000,
      });

      return throwError(() => error);
    })
  );
};
