import { Component, OnInit, inject } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from "@angular/forms";
import { MessageService } from "primeng/api";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from "primeng/inputtextarea";
import { ButtonModule } from "primeng/button";
import { ToastModule } from "primeng/toast";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-contact",
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    InputTextareaModule,
    ButtonModule,
    ToastModule,
  ],
  templateUrl: "./contact.component.html",
  styleUrl: "./contact.component.css",
  providers: [MessageService],
})
export class ContactComponent implements OnInit {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);

  contactForm!: FormGroup;

  ngOnInit(): void {
    this.contactForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      message: ["", [Validators.required, Validators.maxLength(300)]],
    });
  }

  onSubmit(): void {
    if (this.contactForm.valid) {
      console.log("Formulaire de contact soumis:", this.contactForm.value);
      this.messageService.add({
        severity: "success",
        summary: "Succès",
        detail: "Demande de contact envoyée avec succès.",
      });
      this.contactForm.reset();
    } else {
      this.messageService.add({
        severity: "error",
        summary: "Erreur",
        detail: "Veuillez remplir correctement tous les champs.",
      });
    }
  }
}
