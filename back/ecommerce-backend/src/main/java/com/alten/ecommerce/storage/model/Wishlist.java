package com.alten.ecommerce.storage.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Wishlist extends AbstractModel {

    private User user; // com.alten.ecommerce.storage.model.User
    @lombok.Builder.Default
    private List<WishlistItem> items = new ArrayList<>(); // com.alten.ecommerce.storage.model.WishlistItem
}
