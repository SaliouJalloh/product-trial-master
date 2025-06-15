import { Component, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";
import { AuthService } from "../../data-access/auth.service";

@Component({
  selector: "app-auth",
  templateUrl: "./auth.component.html",
  styleUrls: ["./auth.component.scss"],
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    RouterLink,
  ],
})
export class AuthComponent {
  email = "admin@admin.com";
  password = "admin&1234";

  isLoading = signal(false);
  error = signal<string | null>(null);
  success = signal<string | null>(null);

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    this.isLoading.set(true);
    this.error.set(null);
    this.success.set(null);

    this.authService
      .login({ email: this.email, password: this.password })
      .subscribe({
        next: () => {
          this.success.set("Connexion réussie ! Redirection...");
          setTimeout(() => {
            this.router.navigate(["/products/list"]);
          }, 1000);
        },
        error: (err) => {
          this.error.set("Erreur de connexion. Vérifiez vos identifiants.");
          console.error("Login error:", err);
        },
        complete: () => {
          this.isLoading.set(false);
        },
      });
  }
}
