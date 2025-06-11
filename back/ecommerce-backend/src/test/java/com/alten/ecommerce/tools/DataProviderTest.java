package com.alten.ecommerce.tools;

import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.model.User;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DataProviderTest {

    public static String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFsbG8uYWxwaGFAZXhhbXBsZS5jb20iLCJpYXQiOjE3NDIyNDIxMjEsImV4cCI6MTc0MjMyODUyMX0.IFE-064_wtCoxhBOH0MbEuTREYzUIoSFtStdwo0DarU";
    public static long JWT_EXPIRATION = 86400000;

    //----------------------------------------------------------------------------------------------------------------
    //    Building User Entity
    //----------------------------------------------------------------------------------------------------------------
    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .email("john.doe@example.com")
                .password("password&123")
                .firstname("John")
                .build();
    }

    //----------------------------------------------------------------------------------------------------------------
    //    Building User Model
    //----------------------------------------------------------------------------------------------------------------
    public static User buildUser() {
        return User.builder()
                .email("john.doe@example.com")
                .username("john")
                .password("password&123")
                .firstname("John")
                .build();
    }

    public static Product buildProduct() {
        return Product.builder()
                .name("Product Name")
                .code("1234567890")
                .quantity(10)
                .description("Product Description")
                .price(100.0).build();
    }
}
