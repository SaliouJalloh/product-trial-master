import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";

interface LoginResponse {
  token: string;
}

interface SigninRequest {
  email: string;
  password: string;
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly baseUrl = "/api/v1/auth";

  constructor(private readonly http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {
    const request: SigninRequest = { email, password };
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
