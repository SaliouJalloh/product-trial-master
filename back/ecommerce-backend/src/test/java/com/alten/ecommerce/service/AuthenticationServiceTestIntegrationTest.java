package com.alten.ecommerce.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.alten.ecommerce.service.authentication.IAuthService;
import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;
import com.alten.ecommerce.storage.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationServiceTestIntegrationTest {
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

    // Stub
    UserDetails mockUserDetails = mock(UserDetails.class);

    // Configurer les mocks
    when(mockUserDetails.getUsername()).thenReturn("testUser@gmail.com");
  }

  @Test
  void AuthenticateUserWithFormSuccess() {
    String username = "validUser@gmail.com";
    String password = "Password12345@";
  }

  @Test
  @DisplayName("✅ Inscription d'un utilisateur avec un email valide")
  void testRegisterWithValidEmail() {}

  @Test
  void testRegister_UsernameAlreadyExists() {}

  @Test
  public void testRegister_InvalidCredentials() {}

  @Test
  @DisplayName("❌ Échec d'inscription si le username (email/phone) existe déjà")
  void testRegisterWithExistingEmail() {}

  @Test
  void AuthenticateUserWithFormFailedWhenPasswordsNotMatch() {}

  @Test
  void AuthenticateUserWithFormFailedWhenUsernameNotMatch() {}
}
