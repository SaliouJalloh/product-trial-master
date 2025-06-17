package com.alten.ecommerce.storage.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "carts")
public class CartEntity extends AbstractEntity {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private UserEntity user;

  @OneToMany(
      mappedBy = "cart",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @lombok.Builder.Default
  private Set<CartItemEntity> items = new HashSet<>();

  // Helper methods to manage items if needed
  public void addItem(CartItemEntity item) {
    items.add(item);
    item.setCart(this);
  }

  public void removeItem(CartItemEntity item) {
    items.remove(item);
    item.setCart(null);
  }
}
