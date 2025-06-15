import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { environment } from "../../../environments/environment";
import { RegisterRequest } from "./register-request.model";
import { LoginResponse, SigninRequest } from "./auth.model";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly api = environment.apiUrl;
  private readonly baseUrl = "/api/v1/auth";

  constructor(private readonly http: HttpClient) {}

  register(data: RegisterRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/auth/account`, data).pipe(
      tap((response) => {
        localStorage.setItem("token", response.token);
      })
    );
  }

  login(request: SigninRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/token`, request).pipe(
      tap((response) => {
        localStorage.setItem("token", response.token);
      })
    );
  }

  logout(): void {
    localStorage.removeItem("token");
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem("token");
  }
}
