package com.alten.ecommerce.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.alten.ecommerce.EcommerceApplication;
import com.alten.ecommerce.controller.dto.LoginResponseDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.controller.payload.request.SigninRequest;
import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.controller.payload.response.LoginResponse;
import com.alten.ecommerce.exception.UsernameAlreadyExistsException;
import com.alten.ecommerce.service.authentication.IAuthService;
import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.alten.ecommerce.tools.DataProviderTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@SpringBootTest(
    classes = EcommerceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private JwtProviderService jwtProviderService;

  @MockBean private IControllerMapper controllerMapper;

  @MockBean private IAuthService authService;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  @Test
  @DisplayName("✅ Enregistrement réussi - retourne 201 Created avec les bons tokens et cookies")
  void testRegister_success() throws Exception {
    var signupRequest = DataProviderTest.buildSignupRequest();
    var loginResponse = LoginResponse.builder().token("token").type("Bearer").build();
    when(authService.registerUser(any())).thenReturn(loginResponse);
    when(controllerMapper.toLoginResponseDTO(any()))
        .thenReturn(new LoginResponseDTO("token", "Bearer"));

    mockMvc
        .perform(
            post("/api/v1/auth/account")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("❌ Échec - Email déjà utilisé (409 Conflict)")
  void testRegister_emailAlreadyExists() throws Exception {
    var signupRequest = DataProviderTest.buildSignupRequest();
    when(authService.registerUser(any()))
        .thenThrow(new UsernameAlreadyExistsException("Email address already in use"));

    mockMvc
        .perform(
            post("/api/v1/auth/account")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("❌ Échec - Données invalides (400 Bad Request)")
  void testRegister_invalidData() throws Exception {
    var signupRequest = SignupRequest.builder().build();
    when(authService.registerUser(any())).thenThrow(new ConstraintViolationException(null));

    mockMvc
        .perform(
            post("/api/v1/auth/account")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testRegister_KO() throws Exception {
    var signupRequest = DataProviderTest.buildSignupRequest();
    when(authService.registerUser(any())).thenThrow(new RuntimeException("Unexpected error"));

    mockMvc
        .perform(
            post("/api/v1/auth/account")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().is5xxServerError());
  }

  @Test
  void testAuthenticate_success() throws Exception {
    var signinRequest = SigninRequest.builder().email("user@test.com").password("password").build();
    var loginResponse = LoginResponse.builder().token("token").type("Bearer").build();
    when(authService.authenticateUser(any())).thenReturn(loginResponse);
    when(controllerMapper.toLoginResponseDTO(any()))
        .thenReturn(new LoginResponseDTO("token", "Bearer"));

    mockMvc
        .perform(
            post("/api/v1/auth/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signinRequest)))
        .andExpect(status().isOk());
  }

  @Test
  void testAuthenticate_KO() throws Exception {
    var signinRequest = SigninRequest.builder().email("user@test.com").password("password").build();
    when(authService.authenticateUser(any()))
        .thenThrow(new RuntimeException("Authentication failed"));

    mockMvc
        .perform(
            post("/api/v1/auth/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signinRequest)))
        .andExpect(status().is5xxServerError());
  }
}
