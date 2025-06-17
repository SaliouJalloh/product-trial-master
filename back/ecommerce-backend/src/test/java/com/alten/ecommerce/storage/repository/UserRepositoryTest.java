package com.alten.ecommerce.storage.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.tools.DataProviderTest;

@DataJpaTest
@Transactional
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @BeforeEach
  void cleanData() {
    userRepository.deleteAll();
    userRepository.flush();
  }

  @Test
  void initially_empty() {
    // when
    long count = userRepository.count();

    // then
    assertThat(count).isZero();
  }

  @Test
  void existsByEmail_success() {
    // Given
    String email = "contact@gmail.com";
    UserEntity userEntity = DataProviderTest.buildUserEntity();
    userEntity.setUsername(email);
    userEntity.setEmail(email);
    userRepository.save(userEntity);

    // When
    boolean exceptedUserEntity = userRepository.existsByEmail(email);

    // Then
    assertThat(exceptedUserEntity).isTrue();
    assertThat(userRepository.count()).isEqualTo(1);
    assertThat(userRepository.findAll())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactly(userEntity);
  }

  @Test
  void existsByEmail_KO_when_email_not_found() {
    String email = "contact@gmail.com";
    // When
    boolean exceptedUserEntity = userRepository.existsByEmail(email);
    // then
    assertThat(exceptedUserEntity).isFalse();
    assertThat(userRepository.count()).isZero();
  }

  @Test
  void findByUsername_with_email_OK() {
    String email = "john.doe@example.com";
    UserEntity userEntity = DataProviderTest.buildUserEntity();
    userEntity.setUsername(email);
    userRepository.save(userEntity);
    Optional<UserEntity> expected = userRepository.findByEmail(email);

    assertThat(userRepository.count()).isEqualTo(1);
    assertThat(expected).isPresent();
    assertThat(expected.get()).usingRecursiveComparison().isEqualTo(userEntity);
  }

  @Test
  void findByUsername_KO_when_username_not_found() {
    String email = "john.doe@example.com";
    Optional<UserEntity> expected = userRepository.findByEmail(email);
    assertThat(userRepository.count()).isEqualTo(0);
    assertThat(expected).isEmpty();
  }
}
