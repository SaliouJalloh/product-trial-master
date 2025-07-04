package com.alten.ecommerce.storage.model;

import java.math.BigDecimal;

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
public class CartItem extends AbstractModel {

  private Product product;
  private Long productId;
  private String productName;
  private String productImage; // Optional: for easier display
  private BigDecimal productPrice;
  private Integer quantity;
  private BigDecimal itemTotalPrice; // Calculated: quantity * productPrice
}
