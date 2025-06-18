package com.alten.ecommerce.controller.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignupRequest {

  public String firstname;

  public String username;

  public String email;

  public String password;
}
