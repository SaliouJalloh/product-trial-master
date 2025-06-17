package com.alten.ecommerce.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.ecommerce.storage.entity.WishlistEntity;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
  Optional<WishlistEntity> findByUserId(Long userId);
}
