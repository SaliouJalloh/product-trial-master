package com.alten.ecommerce.storage.persistence;

import com.alten.ecommerce.exception.ResourceNotFoundException;
import com.alten.ecommerce.service.cart.model.AddItemToCartRequest;
import com.alten.ecommerce.service.cart.model.UpdateCartItemRequest;
import com.alten.ecommerce.storage.entity.CartEntity;
import com.alten.ecommerce.storage.entity.CartItemEntity;
import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.mapper.ICartItemStorageMapper;
import com.alten.ecommerce.storage.mapper.ICartStorageMapper;
import com.alten.ecommerce.storage.model.Cart;
import com.alten.ecommerce.storage.repository.CartItemRepository;
import com.alten.ecommerce.storage.repository.CartRepository;
import com.alten.ecommerce.storage.repository.ProductRepository;
import com.alten.ecommerce.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartPersistenceService implements ICartPersistenceService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ICartStorageMapper cartMapper;
    private final ICartItemStorageMapper cartItemMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    newCart.setItems(new HashSet<>());
                    return cartRepository.save(newCart);
                });
        return Optional.of(calculateTotalsAndMapToModel(cartEntity));
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long userId, AddItemToCartRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));
        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    newCart.setItems(new HashSet<>());
                    return cartRepository.save(newCart);
                });
        Optional<CartItemEntity> existingItemOpt = cartEntity.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();
        if (existingItemOpt.isPresent()) {
            CartItemEntity itemToUpdate = existingItemOpt.get();
            itemToUpdate.setQuantity(itemToUpdate.getQuantity() + request.getQuantity());
            cartItemRepository.save(itemToUpdate);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cartEntity);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            cartEntity.getItems().add(newItem);
        }
        cartRepository.save(cartEntity);
        return calculateTotalsAndMapToModel(cartEntity);
    }

    @Override
    @Transactional
    public Cart updateCartItem(Long userId, Long itemId, UpdateCartItemRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        CartItemEntity itemToUpdate = cartItemRepository.findById(itemId)
                .filter(item -> item.getCart().getId().equals(cartEntity.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + itemId + " in user's cart"));
        itemToUpdate.setQuantity(request.getQuantity());
        cartItemRepository.save(itemToUpdate);
        return calculateTotalsAndMapToModel(cartEntity);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long userId, Long itemId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        CartItemEntity itemToRemove = cartItemRepository.findById(itemId)
                .filter(item -> item.getCart().getId().equals(cartEntity.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + itemId + " in user's cart"));
        cartEntity.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
        cartRepository.save(cartEntity);
    }

    @Override
    @Transactional
    public Cart clearCart(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        cartEntity.getItems().clear();
        cartRepository.save(cartEntity);
        return calculateTotalsAndMapToModel(cartEntity);
    }

    private Cart calculateTotalsAndMapToModel(CartEntity cartEntity) {
        CartEntity freshCartEntity = cartRepository.findById(cartEntity.getId()).orElse(cartEntity);
        Cart cartModel = cartMapper.entityToModel(freshCartEntity);
        BigDecimal totalCartPrice = BigDecimal.ZERO;
        if (cartModel.getItems() != null) {
            for (var itemModel : cartModel.getItems()) {
                if (itemModel.getProductPrice() != null && itemModel.getQuantity() != null) {
                    BigDecimal itemTotalPrice = itemModel.getProductPrice().multiply(BigDecimal.valueOf(itemModel.getQuantity()));
                    itemModel.setItemTotalPrice(itemTotalPrice);
                    totalCartPrice = totalCartPrice.add(itemTotalPrice);
                }
            }
        }
        cartModel.setTotalPrice(totalCartPrice);
        return cartModel;
    }
}
