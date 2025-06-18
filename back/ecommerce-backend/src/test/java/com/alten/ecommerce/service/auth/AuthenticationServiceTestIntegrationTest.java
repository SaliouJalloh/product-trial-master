package com.alten.ecommerce.service.auth;

import static com.alten.ecommerce.tools.DataProviderTest.ACCESS_TOKEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.alten.ecommerce.controller.payload.request.SigninRequest;
import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.controller.payload.response.LoginResponse;
import com.alten.ecommerce.exception.UsernameAlreadyExistsException;
import com.alten.ecommerce.service.authentication.IAuthService;
import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;
import com.alten.ecommerce.storage.repository.UserRepository;
import com.alten.ecommerce.tools.DataProviderTest;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationServiceTestIntegrationTest {
  private static final String facebookAccessToken =
      "eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0NzQwMmZjNDU1MmUzOGE5OGY3YzY4OTcwMjM5ZDRjODVjZTBiNjkiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiZW1haWwiOiJ5YWFkb3VAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
  private static final String googleIdToken =
      "eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0NzQwMmZjNDU1MmUzOGE5OGY3YzY4OTcwMjM5ZDRjODVjZTBiNjkiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiZW1haWwiOiJ5YWFkb3VAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

  @SpyBean private IUserPersistenceService userPersistenceService;

  @Autowired private IAuthService authenticationService;

  @MockBean private JwtProviderService jwtProviderService;

  @Autowired private UserRepository userRepository;

  @MockBean private RestTemplate restTemplate;

  @Autowired private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    // Réinitialiser la base de données avant chaque test
    userRepository.deleteAll();
    userRepository.flush();
  }

  @Test
  void AuthenticateUserWithFormSuccess() {
    String email = "validUser@gmail.com";
    String password = "Password12345@";

    User user = DataProviderTest.buildUser();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userPersistenceService.saveUser(user);

    SigninRequest signinRequest = SigninRequest.builder().email(email).password(password).build();

    when(jwtProviderService.generateToken(email)).thenReturn(ACCESS_TOKEN);

    LoginResponse loginResponse = authenticationService.authenticateUser(signinRequest);

    assertNotNull(loginResponse);
    assertEquals(ACCESS_TOKEN, loginResponse.getToken());

    verify(jwtProviderService, times(1)).generateToken(anyString());
  }

  @Test
  @DisplayName("✅ Inscription d'un utilisateur avec un email valide")
  void testRegisterWithValidEmail() {
    SignupRequest signupRequest = DataProviderTest.buildSignupRequest();

    when(jwtProviderService.generateToken(signupRequest.getEmail())).thenReturn(ACCESS_TOKEN);

    LoginResponse response = authenticationService.registerUser(signupRequest);

    assertNotNull(response);
    assertNotNull(response.getToken(), "L'Access Token ne doit pas être null");

    Optional<UserEntity> savedUser = userRepository.findByEmail(signupRequest.getEmail());
    assertTrue(savedUser.isPresent(), "L'utilisateur doit être enregistré en base");
    assertEquals(signupRequest.getEmail(), savedUser.get().getEmail());
    assertTrue(
        passwordEncoder.matches("password&123", savedUser.get().getPassword()),
        "Le mot de passe doit être encodé");

    verify(jwtProviderService, times(1)).generateToken(anyString());
  }

  @Test
  public void testRegister_InvalidCredentials() {
    // Arrange
    SignupRequest signupRequest = DataProviderTest.buildSignupRequest();
    signupRequest.setEmail(""); // Invalid email
    signupRequest.setPassword(""); // Invalid password

    // Act & Assert
    EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> authenticationService.registerUser(signupRequest));
    assertTrue(exception.getMessage().contains("empty"));
    verify(userPersistenceService, never()).saveUser(any(User.class));
    verify(jwtProviderService, never()).generateToken(anyString());
  }

  @Test
  @DisplayName("❌ Échec d'inscription si l'email existe déjà")
  void testRegisterWithExistingEmail() {
    // Arrange
    User existingUser = DataProviderTest.buildUser();
    userPersistenceService.saveUser(existingUser);
    // Créer une requête d'inscription avec le même email
    SignupRequest signupRequest = DataProviderTest.buildSignupRequest();
    signupRequest.setEmail(existingUser.getEmail());

    // Act & Assert
    UsernameAlreadyExistsException exception =
        assertThrows(
            UsernameAlreadyExistsException.class,
            () -> authenticationService.registerUser(signupRequest));
    assertEquals("Email address already in use", exception.getMessage());
    verify(jwtProviderService, never()).generateToken(anyString());
  }

  @Test
  void AuthenticateUserWithFormFailedWhenPasswordsNotMatch() {
    // Arrange
    String email = "user@gmail.com";
    String password = "Password12345@";
    User user = DataProviderTest.buildUser();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userPersistenceService.saveUser(user);

    SigninRequest signinRequest =
        SigninRequest.builder().email(email).password("WrongPassword").build();

    // Act & Assert
    Exception exception =
        assertThrows(Exception.class, () -> authenticationService.authenticateUser(signinRequest));
    assertNotNull(exception);
    verify(jwtProviderService, never()).generateToken(anyString());
  }

  @Test
  void AuthenticateUserWithFormFailedWhenUsernameNotMatch() {
    // Arrange
    SigninRequest signinRequest =
        SigninRequest.builder().email("notfound@gmail.com").password("Password12345@").build();

    // Act & Assert
    Exception exception =
        assertThrows(Exception.class, () -> authenticationService.authenticateUser(signinRequest));
    assertNotNull(exception);
    verify(jwtProviderService, never()).generateToken(anyString());
  }
}
