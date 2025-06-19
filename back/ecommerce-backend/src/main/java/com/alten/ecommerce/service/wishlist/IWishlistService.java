package com.alten.ecommerce.service.wishlist;

import com.alten.ecommerce.service.wishlist.model.AddProductToWishlistRequest;
import com.alten.ecommerce.storage.model.Wishlist;

public interface IWishlistService {

  /**
   * Retrieves the wishlist for a given user. If a wishlist does not exist for the user, a new one
   * is created.
   *
   * @param userId The ID of the user.
   * @return The user's wishlist.
   */
  Wishlist getWishlistByUserId(Long userId);

  /**
   * Retrieves the wishlist for the user associated with the principal. If a wishlist does not exist
   * for the user, a new one is created.
   *
   * @param email The email of the user.
   * @return The user's wishlist.
   */
  Wishlist getWishlistForPrincipal(String email);

  /** Adds a product to the user's wishlist by principal. */
  Wishlist addProductToWishlistForPrincipal(String email, AddProductToWishlistRequest request);

  /**
   * Adds a product to the user's wishlist. If the product is already in the wishlist, no action is
   * taken.
   *
   * @param userId The user's ID.
   * @param request The product addition request.
   * @return The updated wishlist.
   */
  Wishlist addProductToWishlist(Long userId, AddProductToWishlistRequest request);

  /** Removes a product from the user's wishlist by principal. */
  Wishlist removeProductFromWishlistForPrincipal(String email, Long productId);

  /**
   * Removes a product from the user's wishlist.
   *
   * @param userId The user's ID.
   * @param productId The product's ID.
   * @return The updated wishlist.
   */
  Wishlist removeProductFromWishlist(Long userId, Long productId);

  /** Clears the user's wishlist by principal. */
  Wishlist clearWishlistForPrincipal(String email);

  /**
   * Clears the user's wishlist.
   *
   * @param userId The user's ID.
   * @return The cleared wishlist.
   */
  Wishlist clearWishlist(Long userId);
}
