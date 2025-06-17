package com.alten.ecommerce.storage.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.alten.ecommerce.exception.ResourceNotFoundException;
import com.alten.ecommerce.storage.mapper.IUserPersistenceMapperImpl;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.repository.UserRepository;
import com.alten.ecommerce.tools.DataProviderTest;

@DataJpaTest
@Import({UserPersistenceService.class, IUserPersistenceMapperImpl.class})
class UserPersistenceServiceTest {

  @Autowired private UserRepository userRepository;

  @Autowired private IUserPersistenceService userPersistenceService;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    userRepository.flush();
  }

  @Test
  void findUserByEmail_success() {
    // When
    User user = userPersistenceService.saveUser(DataProviderTest.buildUser());
    User savedUser = userPersistenceService.findUserByEmail(user.getEmail());

    // Then
    assertThat(user).isNotNull();
    assertThat(savedUser)
        .usingRecursiveComparison()
        .ignoringFields("id", "createdAt", "updatedAt")
        .isEqualTo(user);
  }

  @Test
  void findUserByEmail_KO_WHEN_DATABASE_REPONSE_EMPTY() {
    // When
    ResourceNotFoundException result =
        assertThrows(
            ResourceNotFoundException.class,
            () -> userPersistenceService.findUserByEmail("tata@gmail.com"));

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).contains("User not found");
  }

  @Test
  void saveUser_success() {
    // When
    User actualUser = DataProviderTest.buildUser();
    User expectedUser = userPersistenceService.saveUser(actualUser);

    // Then
    assertNotNull(expectedUser);

    assertThat(actualUser)
        .usingRecursiveComparison()
        .ignoringFields("id", "createdAt", "lastModifiedAt")
        .isEqualTo(expectedUser);
  }

  @Test
  void existsByEmail_success() {
    User inputUser = DataProviderTest.buildUser();

    // When
    userPersistenceService.saveUser(inputUser);

    boolean usernameExists = userPersistenceService.existsByEmail(inputUser.getEmail());

    // Then
    assertTrue(usernameExists);
  }

  @Test
  void existsByEmail_KO() {
    boolean usernameExists = userPersistenceService.existsByEmail("sam@bordeaux.fr");
    assertFalse(usernameExists);
  }
}
