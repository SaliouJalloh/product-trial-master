package com.alten.ecommerce.storage.repository;

import com.alten.ecommerce.storage.entity.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItemEntity, Long> {
    Optional<WishlistItemEntity> findByWishlistIdAndProductId(Long wishlistId, Long productId);
    // On pourrait aussi vouloir supprimer tous les items d'une wishlist
    // void deleteByWishlistId(Long wishlistId);
}
