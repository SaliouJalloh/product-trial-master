package com.alten.ecommerce.service.wishlist.model;

import com.alten.ecommerce.storage.model.AbstractModel;
import com.alten.ecommerce.storage.model.Product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WishlistItem extends AbstractModel {

  private Product product;
}
