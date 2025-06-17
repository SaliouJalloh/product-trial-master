package com.alten.ecommerce.storage.entity;

import com.alten.ecommerce.storage.enums.InventoryStatus;

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
@Table(name = "products")
public class ProductEntity extends AbstractEntity {

  @Column(unique = true)
  private String code;

  @Column(nullable = false)
  private String name;

  private String description;

  private String image;

  private String category;

  private Double price;

  private Integer quantity;

  private String internalReference;

  private Long shellId;

  @Enumerated(EnumType.STRING)
  private InventoryStatus inventoryStatus;

  private Double rating;
}
