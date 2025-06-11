package com.alten.ecommerce.service.authentication;

import com.alten.ecommerce.controller.payload.request.SigninRequest;
import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.controller.payload.response.LoginResponse;
import com.alten.ecommerce.exception.UsernameAlreadyExistsException;
import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;
    private final IUserPersistenceService userPersistenceService;

    @Override
    @Transactional
    public LoginResponse registerUser(SignupRequest signUpRequest) {
        log.info("start user registration for username: {}", signUpRequest.email);
        String userEmail = signUpRequest.email;
        if (StringUtils.isBlank(userEmail) || StringUtils.isBlank(signUpRequest.password)) {
            log.error("User email or password is empty");
            throw new EntityNotFoundException("Email or password is empty");
        }
        if (userPersistenceService.existsByEmail(signUpRequest.email)) {
            log.error("User already exists with username: {}", userEmail);
            throw new UsernameAlreadyExistsException("Email address already in use");
        }

        User user = User.builder()
                .email(signUpRequest.email)
                .username(signUpRequest.username)
                .password(passwordEncoder.encode(signUpRequest.password))
                .firstname(signUpRequest.firstname)
                .build();
        userPersistenceService.saveUser(user);
        String jwt = jwtProviderService.generateToken(signUpRequest.email);

        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .build();
    }

    @Override
    public LoginResponse authenticateUser(SigninRequest signinRequest) {
        log.info("start authenticateUser : {}", signinRequest.email);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.email, signinRequest.password));

        String jwt = jwtProviderService.generateToken(signinRequest.email);

        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .build();
    }
}
