import { ApplicationConfig } from "@angular/core";
import { provideRouter } from "@angular/router";
import { APP_ROUTES } from "./app.routes";
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import { errorInterceptor } from "./shared/interceptors/error.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(APP_ROUTES),
    provideHttpClient(withInterceptors([errorInterceptor])),
  ],
};
