import { Component } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { AuthService } from "app/auth/data-access/auth.service";
import { RegisterRequest } from "../../data-access/register-request.model";
import { Router } from "@angular/router";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
  imports: [ReactiveFormsModule],
  standalone: true,
})
export class RegisterComponent {
  registerForm: FormGroup;
  loading = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.registerForm = this.fb.group({
      firstname: ["", [Validators.required, Validators.maxLength(50)]],
      username: [
        "",
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ],
      ],
      email: [
        "",
        [Validators.required, Validators.email, Validators.maxLength(50)],
      ],
      password: [
        "",
        [
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(40),
        ],
      ],
    });
  }

  onSubmit() {
    this.error = null;
    this.success = null;
    if (this.registerForm.invalid) {
      this.error = "Veuillez remplir correctement tous les champs.";
      return;
    }
    this.loading = true;
    const data: RegisterRequest = this.registerForm.value;
    this.authService.register(data).subscribe({
      next: () => {
        this.success =
          "Inscription réussie ! Vous pouvez maintenant vous connecter.";
        this.loading = false;
        this.registerForm.reset();
        setTimeout(() => this.router.navigate(["/login"]), 2000);
      },
      error: (err) => {
        this.error = err.error?.message || "Erreur lors de l'inscription.";
        this.loading = false;
      },
    });
  }
}
