package com.alten.ecommerce.storage.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class Cart extends AbstractModel {

  private User user;
  private Long userId;
  @lombok.Builder.Default private List<CartItem> items = new ArrayList<>();
  private BigDecimal totalPrice; // Calculated in service layer
}
