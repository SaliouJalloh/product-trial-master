package com.alten.ecommerce.storage.persistence;

import java.util.Optional;

import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;
import com.alten.ecommerce.storage.model.Cart;

public interface ICartPersistenceService {
  Optional<Cart> findByUserId(Long userId);

  Cart addItemToCart(Long userId, AddItemToCartRequest request);

  Cart updateCartItem(Long userId, Long itemId, UpdateCartItemRequest request);

  void removeItemFromCart(Long userId, Long itemId);

  Cart clearCart(Long userId);
}
