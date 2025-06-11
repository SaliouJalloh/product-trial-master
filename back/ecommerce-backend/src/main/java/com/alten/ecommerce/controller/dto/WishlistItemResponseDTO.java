package com.alten.ecommerce.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItemResponseDTO {
    private Long id; // ID de WishlistItemEntity
    private Long productId;
    private String productName;
    private String productImage;
    private Double productPrice;
    private LocalDateTime addedAt; // createdAt de WishlistItemEntity
}
