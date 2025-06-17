package com.alten.ecommerce.service.cart;

import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;
import com.alten.ecommerce.storage.model.Cart;

public interface ICartService {

  Cart getCartByUserId(Long userId);

  /** Récupère le panier pour l'utilisateur associé au principal Spring Security. */
  Cart getCartForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser);

  /** Ajoute un article au panier pour l'utilisateur associé au principal Spring Security. */
  Cart addItemToCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser,
      AddItemToCartRequest request);

  /** Met à jour un article du panier pour l'utilisateur associé au principal Spring Security. */
  Cart updateCartItemForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser,
      Long itemId,
      UpdateCartItemRequest request);

  /** Supprime un article du panier pour l'utilisateur associé au principal Spring Security. */
  void removeItemFromCartForPrincipal(
      org.springframework.security.core.userdetails.User springSecurityUser, Long itemId);

  /** Vide le panier pour l'utilisateur associé au principal Spring Security. */
  Cart clearCartForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser);
}
