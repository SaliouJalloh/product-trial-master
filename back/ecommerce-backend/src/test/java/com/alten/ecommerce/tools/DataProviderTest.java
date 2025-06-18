package com.alten.ecommerce.tools;

import org.springframework.boot.test.context.SpringBootTest;

import com.alten.ecommerce.controller.payload.request.SignupRequest;
import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.model.User;

@SpringBootTest
public class DataProviderTest {

  public static String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9";

  // ----------------------------------------------------------------------------------------------------------------
  //    Building User Entity
  // ----------------------------------------------------------------------------------------------------------------
  public static UserEntity buildUserEntity() {
    return UserEntity.builder()
        .firstname("John")
        .email("john.doe@example.com")
        .password("password&123")
        .build();
  }

  // ----------------------------------------------------------------------------------------------------------------
  //    Building User Model
  // ----------------------------------------------------------------------------------------------------------------
  public static User buildUser() {
    return User.builder()
        .firstname("John")
        .email("john.doe@example.com")
        .username("john")
        .password("password&123")
        .build();
  }

  public static SignupRequest buildSignupRequest() {
    return SignupRequest.builder()
        .firstname("John")
        .username("john")
        .email("john.doe@example.com")
        .password("password&123")
        .build();
  }

  public static Product buildProduct() {
    return Product.builder()
        .name("Product Name")
        .code("1234567890")
        .quantity(10)
        .description("Product Description")
        .price(100.0)
        .build();
  }
}
