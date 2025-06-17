package com.alten.ecommerce.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponseDTO {
  private Long id; // ID de WishlistEntity
  private Long userId;
  private List<WishlistItemResponseDTO> items;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
