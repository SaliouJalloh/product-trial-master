package com.alten.ecommerce.service.wishlist.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToWishlistRequest {

  @NotNull(message = "Product ID cannot be null")
  private Long productId;
}
