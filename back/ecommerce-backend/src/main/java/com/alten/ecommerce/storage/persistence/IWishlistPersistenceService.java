package com.alten.ecommerce.storage.persistence; // Corrigé

import java.util.Optional;

import com.alten.ecommerce.storage.model.Wishlist;

public interface IWishlistPersistenceService {

  Optional<Wishlist> findByUserId(Long userId);

  Optional<Wishlist> findById(Long wishlistId);

  Wishlist createWishlist(Long userId);

  Wishlist addProductToWishlist(Long wishlistId, Long productId);

  Wishlist removeProductFromWishlist(Long wishlistId, Long productId);

  Wishlist clearWishlist(Long wishlistId);

  void deleteWishlist(Long wishlistId);
}
