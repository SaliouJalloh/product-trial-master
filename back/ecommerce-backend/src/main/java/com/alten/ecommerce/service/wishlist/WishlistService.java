package com.alten.ecommerce.service.wishlist;

import com.alten.ecommerce.exception.ResourceNotFoundException;
import com.alten.ecommerce.service.wishlist.model.AddProductToWishlistRequest;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.model.Wishlist;
import com.alten.ecommerce.storage.persistence.IProductPersistenceService;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;
import com.alten.ecommerce.storage.persistence.IWishlistPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional // Toutes les méthodes publiques seront transactionnelles par défaut
@Slf4j
public class WishlistService implements IWishlistService {

    private final IWishlistPersistenceService wishlistPersistenceService;
    private final IUserPersistenceService userPersistenceService;
    private final IProductPersistenceService productPersistenceService;

    @Override
    public Wishlist getWishlistByUserId(Long userId) {
        log.debug("Attempting to get wishlist for user ID: {}", userId);
        // Vérifier si l'utilisateur existe. Lance une exception si non trouvé.
        userPersistenceService.findUserById(userId);

        return wishlistPersistenceService.findByUserId(userId)
                .orElseGet(() -> {
                    log.info("No wishlist found for user ID: {}. Creating a new one.", userId);
                    return wishlistPersistenceService.createWishlist(userId);
                });
    }

    @Override
    public Wishlist getWishlistForPrincipal(User springSecurityUser) {
        User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
        if (user == null) throw new ResourceNotFoundException("User not found...");
        return wishlistPersistenceService.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found for user: " + user.getId()));
    }

    @Override
    public Wishlist addProductToWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser, AddProductToWishlistRequest request) {
        User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
        if (user == null) throw new ResourceNotFoundException("User not found...");
        return addProductToWishlist(user.getId(), request);
    }

    @Override
    public Wishlist removeProductFromWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser, Long productId) {
        User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
        if (user == null) throw new ResourceNotFoundException("User not found...");
        return removeProductFromWishlist(user.getId(), productId);
    }

    @Override
    public Wishlist clearWishlistForPrincipal(org.springframework.security.core.userdetails.User springSecurityUser) {
        User user = userPersistenceService.findUserByEmail(springSecurityUser.getUsername());
        if (user == null) throw new ResourceNotFoundException("User not found...");
        return clearWishlist(user.getId());
    }

    @Override
    public Wishlist addProductToWishlist(Long userId, AddProductToWishlistRequest request) {
        log.debug("Attempting to add product ID: {} to wishlist for user ID: {}", request.getProductId(), userId);
        // Vérifier si l'utilisateur et le produit existent. Lance des exceptions si non trouvés.
        userPersistenceService.findUserById(userId);
        Product product = productPersistenceService.findProductById(request.getProductId()); // Assure que le produit existe

        // Récupérer ou créer la wishlist
        Wishlist wishlist = wishlistPersistenceService.findByUserId(userId)
                .orElseGet(() -> wishlistPersistenceService.createWishlist(userId));

        // La logique de vérification si le produit existe déjà et l'ajout effectif
        // est gérée par wishlistPersistenceService.addProductToWishlist
        // qui devrait être idempotente ou retourner la wishlist non modifiée si l'item existe.
        // Si addProductToWishlist ne recharge pas la wishlist, il faut le faire ici.
        return wishlistPersistenceService.addProductToWishlist(wishlist.getId(), product.getId());
    }

    @Override
    public Wishlist removeProductFromWishlist(Long userId, Long productId) {
        log.debug("Attempting to remove product ID: {} from wishlist for user ID: {}", productId, userId);
        // Vérifier si l'utilisateur et le produit existent.
        userPersistenceService.findUserById(userId);
        productPersistenceService.findProductById(productId); // Assure que le produit existe

        Wishlist wishlist = wishlistPersistenceService.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Wishlist not found for user ID: {} during remove operation.", userId);
                    return new ResourceNotFoundException("Wishlist not found for user: " + userId);
                });

        // La logique de suppression est gérée par la couche persistance.
        return wishlistPersistenceService.removeProductFromWishlist(wishlist.getId(), productId);
    }

    @Override
    public Wishlist clearWishlist(Long userId) {
        log.debug("Attempting to clear wishlist for user ID: {}", userId);
        userPersistenceService.findUserById(userId);

        Wishlist wishlist = wishlistPersistenceService.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Wishlist not found for user ID: {} during clear operation.", userId);
                    return new ResourceNotFoundException("Wishlist not found for user: " + userId);
                });

        return wishlistPersistenceService.clearWishlist(wishlist.getId());
    }
}
