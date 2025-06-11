package com.alten.ecommerce.controller.mapper;

import com.alten.ecommerce.controller.dto.CartItemResponseDTO;
import com.alten.ecommerce.controller.dto.CartResponseDTO;
import com.alten.ecommerce.controller.dto.LoginResponseDTO;
import com.alten.ecommerce.controller.dto.ProductDTO;
import com.alten.ecommerce.controller.dto.WishlistItemResponseDTO; // Ajouté
import com.alten.ecommerce.controller.dto.WishlistResponseDTO;   // Ajouté
import com.alten.ecommerce.controller.payload.response.LoginResponse;
import com.alten.ecommerce.storage.model.Cart;
import com.alten.ecommerce.storage.model.CartItem;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.model.Wishlist;     // Ajouté
import com.alten.ecommerce.storage.model.WishlistItem; // Ajouté
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Ajouté pour les mappings explicites si besoin

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IControllerMapper {

    ProductDTO toProductDTO(Product product);

    LoginResponseDTO toLoginResponseDTO(LoginResponse loginResponse);

    // Cart Mappings
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "productImage", target = "productImage")
    @Mapping(source = "productPrice", target = "productPrice")
    CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem);

    List<CartItemResponseDTO> toCartItemResponseDTOList(List<CartItem> cartItems);

    @Mapping(source = "userId", target = "userId")
    CartResponseDTO toCartResponseDTO(Cart cart);

    // Wishlist Mappings
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.image", target = "productImage")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "createdAt", target = "addedAt") // createdAt de WishlistItemEntity
    WishlistItemResponseDTO toWishlistItemResponseDTO(WishlistItem wishlistItem);

    List<WishlistItemResponseDTO> toWishlistItemResponseDTOList(List<WishlistItem> wishlistItems);

    @Mapping(source = "user.id", target = "userId")
    WishlistResponseDTO toWishlistResponseDTO(Wishlist wishlist);
}
