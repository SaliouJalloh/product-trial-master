package com.alten.ecommerce.service.wishlist;

import com.alten.ecommerce.service.wishlist.model.AddProductToWishlistRequest;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.model.Wishlist; // Modifié pour pointer vers storage.model

public interface IWishlistService {

    /**
     * Retrieves the wishlist for a given user.
     * If a wishlist does not exist for the user, a new one is created.
     *
     * @param userId The ID of the user.
     * @return The user's wishlist.
     */
    Wishlist getWishlistByUserId(Long userId);

    /**
     * Retrieves the wishlist for the user associated with the principal.
     * If a wishlist does not exist for the user, a new one is created.
     *
     * @param springSecurityUser The principal user.
     * @return The user's wishlist.
     */
    Wishlist getWishlistForPrincipal(User springSecurityUser);

    /**
     * Adds a product to the user's wishlist by principal.
     */
    Wishlist addProductToWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser, AddProductToWishlistRequest request);

    /**
     * Adds a product to the user's wishlist.
     * If the product is already in the wishlist, no action is taken.
     *
     * @param userId The user's ID.
     * @param request The product addition request.
     * @return The updated wishlist.
     */
    Wishlist addProductToWishlist(Long userId, AddProductToWishlistRequest request);

    /**
     * Removes a product from the user's wishlist by principal.
     */
    Wishlist removeProductFromWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser, Long productId);

    /**
     * Removes a product from the user's wishlist.
     *
     * @param userId The user's ID.
     * @param productId The product's ID.
     * @return The updated wishlist.
     */
    Wishlist removeProductFromWishlist(Long userId, Long productId);

    /**
     * Clears the user's wishlist by principal.
     */
    Wishlist clearWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser);

    /**
     * Clears the user's wishlist.
     *
     * @param userId The user's ID.
     * @return The cleared wishlist.
     */
    Wishlist clearWishlist(Long userId);
}
