package com.alten.ecommerce.service.cart;

import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;
import com.alten.ecommerce.storage.model.Cart;

public interface ICartService {

  Cart getCartByUserId(Long userId);

  /** Récupère le panier pour l'utilisateur identifié par email. */
  Cart getCartForPrincipal(String email);

  /** Ajoute un article au panier pour l'utilisateur identifié par email. */
  Cart addItemToCartForPrincipal(String email, AddItemToCartRequest request);

  /** Met à jour un article du panier pour l'utilisateur identifié par email. */
  Cart updateCartItemForPrincipal(String email, Long itemId, UpdateCartItemRequest request);

  /** Supprime un article du panier pour l'utilisateur identifié par email. */
  void removeItemFromCartForPrincipal(String email, Long itemId);

  /** Vide le panier pour l'utilisateur identifié par email. */
  Cart clearCartForPrincipal(String email);
}
