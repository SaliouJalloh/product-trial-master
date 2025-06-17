package com.alten.ecommerce.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {

  private String username;

  @Column(length = 50, nullable = false)
  private String firstname;

  @Column(unique = true)
  private String email;

  private String password;
}
