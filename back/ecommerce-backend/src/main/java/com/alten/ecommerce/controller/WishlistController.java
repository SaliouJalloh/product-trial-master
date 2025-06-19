package com.alten.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.alten.ecommerce.controller.dto.WishlistResponseDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.service.wishlist.IWishlistService;
import com.alten.ecommerce.service.wishlist.model.AddProductToWishlistRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("${application.frontend.basePath}")
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@Slf4j
public class WishlistController {

  private final IWishlistService wishlistService;
  private final IControllerMapper controllerMapper;

  @GetMapping
  public ResponseEntity<WishlistResponseDTO> getWishlist(
      @AuthenticationPrincipal User springSecurityUser) {
    String email = springSecurityUser.getUsername();
    WishlistResponseDTO dto =
        controllerMapper.toWishlistResponseDTO(wishlistService.getWishlistForPrincipal(email));
    return ResponseEntity.ok(dto);
  }

  @PostMapping("/items")
  public ResponseEntity<WishlistResponseDTO> addProductToWishlist(
      @AuthenticationPrincipal User springSecurityUser,
      @Valid @RequestBody AddProductToWishlistRequest request) {
    String email = springSecurityUser.getUsername();
    WishlistResponseDTO wishlistResponseDTO =
        controllerMapper.toWishlistResponseDTO(
            wishlistService.addProductToWishlistForPrincipal(email, request));
    return ResponseEntity.ok(wishlistResponseDTO);
  }

  @DeleteMapping("/items/{productId}")
  public ResponseEntity<WishlistResponseDTO> removeProductFromWishlist(
      @AuthenticationPrincipal User springSecurityUser, @PathVariable Long productId) {
    String email = springSecurityUser.getUsername();
    WishlistResponseDTO wishlistResponseDTO =
        controllerMapper.toWishlistResponseDTO(
            wishlistService.removeProductFromWishlistForPrincipal(email, productId));
    return ResponseEntity.ok(wishlistResponseDTO);
  }

  @DeleteMapping
  public ResponseEntity<WishlistResponseDTO> clearWishlist(
      @AuthenticationPrincipal User springSecurityUser) {
    String email = springSecurityUser.getUsername();
    WishlistResponseDTO wishlistResponseDTO =
        controllerMapper.toWishlistResponseDTO(wishlistService.clearWishlistForPrincipal(email));
    return ResponseEntity.ok(wishlistResponseDTO);
  }
}
