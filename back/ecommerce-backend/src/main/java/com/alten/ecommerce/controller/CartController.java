package com.alten.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.alten.ecommerce.controller.dto.CartResponseDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.service.cart.ICartService;
import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("${application.frontend.basePath}")
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

  private final ICartService cartService;
  private final IControllerMapper controllerMapper;

  @GetMapping
  public ResponseEntity<CartResponseDTO> getCart(@AuthenticationPrincipal User springSecurityUser) {
    String email = springSecurityUser.getUsername();
    CartResponseDTO cartResponseDTO =
        controllerMapper.toCartResponseDTO(cartService.getCartForPrincipal(email));
    return ResponseEntity.ok(cartResponseDTO);
  }

  @PostMapping("/items")
  public ResponseEntity<CartResponseDTO> addItemToCart(
      @AuthenticationPrincipal User springSecurityUser,
      @Valid @RequestBody AddItemToCartRequest request) {
    String email = springSecurityUser.getUsername();
    CartResponseDTO cartResponseDTO =
        controllerMapper.toCartResponseDTO(cartService.addItemToCartForPrincipal(email, request));
    return ResponseEntity.ok(cartResponseDTO);
  }

  @PutMapping("/items/{itemId}")
  public ResponseEntity<CartResponseDTO> updateCartItem(
      @AuthenticationPrincipal User springSecurityUser,
      @PathVariable Long itemId,
      @Valid @RequestBody UpdateCartItemRequest request) {
    String email = springSecurityUser.getUsername();
    CartResponseDTO cartResponseDTO =
        controllerMapper.toCartResponseDTO(
            cartService.updateCartItemForPrincipal(email, itemId, request));
    return ResponseEntity.ok(cartResponseDTO);
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<Void> removeItemFromCart(
      @AuthenticationPrincipal User springSecurityUser, @PathVariable Long itemId) {
    String email = springSecurityUser.getUsername();
    cartService.removeItemFromCartForPrincipal(email, itemId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<CartResponseDTO> clearCart(
      @AuthenticationPrincipal User springSecurityUser) {
    String email = springSecurityUser.getUsername();
    CartResponseDTO cartResponseDTO =
        controllerMapper.toCartResponseDTO(cartService.clearCartForPrincipal(email));
    return ResponseEntity.ok(cartResponseDTO);
  }
}
