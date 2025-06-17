package com.alten.ecommerce.controller.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
  @Size(max = 50)
  @NotBlank(message = "firstname is required")
  public String firstname;

  @NotBlank
  @Size(min = 3, max = 20)
  public String username;

  @Size(max = 50)
  @NotBlank(message = "email is required")
  @Email(message = "email format is not valid")
  public String email;

  @NotBlank(message = "password is required")
  @Size(min = 6, max = 40)
  public String password;
}
