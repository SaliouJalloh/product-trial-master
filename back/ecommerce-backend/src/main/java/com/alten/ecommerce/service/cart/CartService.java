package com.alten.ecommerce.service.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.ecommerce.exception.ResourceNotFoundException;
import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;
import com.alten.ecommerce.storage.model.Cart;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

  private final IUserPersistenceService userPersistenceService; // Pour lookup user
  private final com.alten.ecommerce.storage.persistence.ICartPersistenceService
      cartPersistenceService; // Pour accès cart persistence

  @Override
  @Transactional(readOnly = true)
  public Cart getCartByUserId(Long userId) {
    User user = userPersistenceService.findUserById(userId);
    if (user == null) throw new ResourceNotFoundException("User not found with id: " + userId);
    return cartPersistenceService
        .findByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
  }

  @Override
  public Cart getCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser) {
    User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
    if (user == null) throw new ResourceNotFoundException("User not found...");
    return getCartByUserId(user.getId());
  }

  @Override
  public Cart addItemToCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser,
      AddItemToCartRequest request) {
    User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
    if (user == null) throw new ResourceNotFoundException("User not found...");
    return cartPersistenceService.addItemToCart(user.getId(), request);
  }

  @Override
  public Cart updateCartItemForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser,
      Long itemId,
      UpdateCartItemRequest request) {
    User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
    if (user == null) throw new ResourceNotFoundException("User not found...");
    return cartPersistenceService.updateCartItem(user.getId(), itemId, request);
  }

  @Override
  public void removeItemFromCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser, Long itemId) {
    User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
    if (user == null) throw new ResourceNotFoundException("User not found...");
    cartPersistenceService.removeItemFromCart(user.getId(), itemId);
  }

  @Override
  public Cart clearCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser) {
    User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
    if (user == null) throw new ResourceNotFoundException("User not found...");
    return cartPersistenceService.clearCart(user.getId());
  }
}
