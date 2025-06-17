package com.alten.ecommerce.service.wishlist.model;

import java.util.ArrayList;
import java.util.List;

import com.alten.ecommerce.storage.model.AbstractModel;
import com.alten.ecommerce.storage.model.User;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Wishlist extends AbstractModel {

  private User user;
  @lombok.Builder.Default private List<WishlistItem> items = new ArrayList<>();
}
