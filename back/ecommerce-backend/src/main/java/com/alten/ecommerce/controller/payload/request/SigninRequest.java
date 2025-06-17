package com.alten.ecommerce.controller.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SigninRequest {

  public String email;

  public String password;
}
