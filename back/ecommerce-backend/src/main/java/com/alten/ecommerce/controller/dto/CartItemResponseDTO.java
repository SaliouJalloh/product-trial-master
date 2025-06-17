package com.alten.ecommerce.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
  private Long id;
  private Long productId;
  private String productName;
  private String productImage;
  private BigDecimal productPrice;
  private Integer quantity;
  private BigDecimal itemTotalPrice;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
