package com.alten.ecommerce.service.authentication;

import com.alten.ecommerce.controller.payload.request.SigninRequest;
import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.controller.payload.response.LoginResponse;

public interface IAuthService {
    LoginResponse registerUser(SignupRequest signupRequest);

    LoginResponse authenticateUser(SigninRequest signinRequest);
}
