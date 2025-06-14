package com.alten.ecommerce.controller;

import com.alten.ecommerce.controller.dto.WishlistResponseDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.service.wishlist.IWishlistService;
import com.alten.ecommerce.service.wishlist.model.AddProductToWishlistRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("${application.frontend.basePath}")
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@Slf4j
public class WishlistController {

    private final IWishlistService wishlistService;
    private final IControllerMapper controllerMapper;

    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getWishlist(@AuthenticationPrincipal com.alten.ecommerce.storage.model.User springSecurityUser) {
        WishlistResponseDTO dto = controllerMapper.toWishlistResponseDTO(
                wishlistService.getWishlistForPrincipal(springSecurityUser)
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/items")
    public ResponseEntity<WishlistResponseDTO> addProductToWishlist(@AuthenticationPrincipal User springSecurityUser,
                                                                    @Valid @RequestBody AddProductToWishlistRequest request) {
        WishlistResponseDTO wishlistResponseDTO = controllerMapper.toWishlistResponseDTO(
                wishlistService.addProductToWishlistForPrincipal(springSecurityUser, request)
        );
        return ResponseEntity.ok(wishlistResponseDTO);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<WishlistResponseDTO> removeProductFromWishlist(@AuthenticationPrincipal User springSecurityUser,
                                                                         @PathVariable Long productId) {
        WishlistResponseDTO wishlistResponseDTO = controllerMapper.toWishlistResponseDTO(
                wishlistService.removeProductFromWishlistForPrincipal(springSecurityUser, productId)
        );
        return ResponseEntity.ok(wishlistResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<WishlistResponseDTO> clearWishlist(@AuthenticationPrincipal User springSecurityUser) {
        WishlistResponseDTO wishlistResponseDTO = controllerMapper.toWishlistResponseDTO(
                wishlistService.clearWishlistForPrincipal(springSecurityUser)
        );
        return ResponseEntity.ok(wishlistResponseDTO);
    }
}
