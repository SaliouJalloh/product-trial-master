package com.alten.ecommerce.storage.model;

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
public class WishlistItem extends AbstractModel {

    private Product product;
    // La référence à Wishlist n'est pas nécessaire ici, la relation est portée par Wishlist.items
}
