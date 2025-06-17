package com.alten.ecommerce.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.ecommerce.storage.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
  Optional<CartEntity> findByUserId(Long userId);
  // Optional: boolean existsByUserId(Long userId);
}
