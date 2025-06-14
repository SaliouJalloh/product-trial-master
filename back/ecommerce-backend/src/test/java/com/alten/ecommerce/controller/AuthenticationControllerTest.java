package com.alten.ecommerce.controller;

import com.alten.ecommerce.EcommerceApplication;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.service.authentication.IAuthService;
import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureJsonTesters
@SpringBootTest(classes = EcommerceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtProviderService jwtProviderService;

    @MockBean
    private IControllerMapper controllerMapper;

    @MockBean
    private IAuthService authService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);


    }

    @Test
    @DisplayName("✅ Enregistrement réussi - retourne 201 Created avec les bons tokens et cookies")
    void testRegister_success() throws Exception {

    }

    @Test
    @DisplayName("❌ Échec - Email déjà utilisé (409 Conflict)")
    void testRegister_emailAlreadyExists() throws Exception {

    }

    @Test
    @DisplayName("❌ Échec - Données invalides (400 Bad Request)")
    void testRegister_invalidData() throws Exception {


    }


    @Test
    void testRegister_KO() throws Exception {

    }


    @Test
    void testAuthenticate_success() throws Exception {

    }


    @Test
    void testAuthenticate_KO() throws Exception {

    }

}
