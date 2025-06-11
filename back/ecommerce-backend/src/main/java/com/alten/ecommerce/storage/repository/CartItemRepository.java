package com.alten.ecommerce.storage.repository;

import com.alten.ecommerce.storage.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    // Find an item in a specific cart for a specific product
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
}
