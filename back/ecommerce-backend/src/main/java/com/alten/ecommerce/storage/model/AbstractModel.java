package com.alten.ecommerce.storage.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractModel {

  private Long id;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
