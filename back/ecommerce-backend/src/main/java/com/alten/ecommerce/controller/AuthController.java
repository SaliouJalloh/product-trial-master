package com.alten.ecommerce.controller;

import com.alten.ecommerce.controller.dto.LoginResponseDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.controller.payload.request.SigninRequest;
import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.controller.payload.response.LoginResponse;
import com.alten.ecommerce.service.authentication.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("${application.frontend.basePath}")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;
    private final IControllerMapper controllerMapper;

    @PostMapping("/token")
    public LoginResponseDTO authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        log.info("Authenticate request received for user: {}", signinRequest.email);
        LoginResponse loginResponse = authService.authenticateUser(signinRequest);
        return controllerMapper.toLoginResponseDTO(loginResponse);
    }

    @PostMapping("/account")
    public LoginResponseDTO registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("Register request received for user: {}", signUpRequest.email);
        LoginResponse loginResponse = authService.registerUser(signUpRequest);
        return controllerMapper.toLoginResponseDTO(loginResponse);
    }
}
