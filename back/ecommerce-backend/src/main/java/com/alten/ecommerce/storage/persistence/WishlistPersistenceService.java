package com.alten.ecommerce.storage.persistence;

import com.alten.ecommerce.exception.ResourceNotFoundException;
import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.entity.WishlistEntity;
import com.alten.ecommerce.storage.entity.WishlistItemEntity;
import com.alten.ecommerce.storage.mapper.CycleAvoidingMappingContext;
import com.alten.ecommerce.storage.mapper.IWishlistStorageMapper;
import com.alten.ecommerce.storage.model.Wishlist;
import com.alten.ecommerce.storage.repository.ProductRepository;
import com.alten.ecommerce.storage.repository.UserRepository;
import com.alten.ecommerce.storage.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistPersistenceService implements IWishlistPersistenceService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final IWishlistStorageMapper wishlistStorageMapper;

    @Override
    public Optional<Wishlist> findByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .map(entity -> wishlistStorageMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<Wishlist> findById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .map(entity -> wishlistStorageMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Wishlist createWishlist(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        WishlistEntity wishlistEntity = new WishlistEntity();
        wishlistEntity.setUser(userEntity);
        // Les timestamps sont gérés par @PrePersist/@PreUpdate dans l'entité
        return wishlistStorageMapper.toModel(wishlistRepository.save(wishlistEntity), new CycleAvoidingMappingContext());
    }

    @Override
    public Wishlist addProductToWishlist(Long wishlistId, Long productId) {
        WishlistEntity wishlistEntity = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Vérifier si le produit est déjà dans la wishlist pour éviter les doublons
        boolean productExists = wishlistEntity.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));

        if (!productExists) {
            WishlistItemEntity wishlistItemEntity = new WishlistItemEntity();
            wishlistItemEntity.setProduct(productEntity);
            // La liaison bidirectionnelle est gérée par wishlistEntity.addItem()
            wishlistEntity.addItem(wishlistItemEntity); // Ceci devrait aussi setter wishlistItemEntity.setWishlist(this)
            wishlistRepository.save(wishlistEntity); // Sauvegarder la wishlist mettra à jour/créera l'item grâce à CascadeType.ALL
        }
        // Retourner la wishlist potentiellement mise à jour (ou non si l'item existait déjà)
        return wishlistStorageMapper.toModel(wishlistEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Wishlist removeProductFromWishlist(Long wishlistId, Long productId) {
        WishlistEntity wishlistEntity = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));

        // Trouver l'item à supprimer
        Optional<WishlistItemEntity> itemToRemoveOpt = wishlistEntity.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (itemToRemoveOpt.isPresent()) {
            WishlistItemEntity itemToRemove = itemToRemoveOpt.get();
            wishlistEntity.removeItem(itemToRemove); // Gère la liaison bidirectionnelle et orphanRemoval s'occupe de la suppression de l'item
            wishlistRepository.save(wishlistEntity);
        }
        // Si l'item n'est pas trouvé, on ne fait rien et on retourne la wishlist actuelle
        return wishlistStorageMapper.toModel(wishlistEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Wishlist clearWishlist(Long wishlistId) {
        WishlistEntity wishlistEntity = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + wishlistId));
        wishlistEntity.getItems().clear(); // orphanRemoval=true devrait s'occuper de supprimer les WishlistItemEntity de la DB
        // Ou, pour être plus explicite sur la suppression des items orphelins :
        // wishlistItemRepository.deleteAll(wishlistEntity.getItems()); // Cela pourrait être redondant avec orphanRemoval
        // wishlistEntity.setItems(new ArrayList<>()); // Pour vider la collection côté entité
        wishlistRepository.save(wishlistEntity);

        // Pour s'assurer que la liste d'items retournée est vide:
        Wishlist model = wishlistStorageMapper.toModel(wishlistEntity, new CycleAvoidingMappingContext());
        model.setItems(Collections.emptyList()); // Assurer que le modèle retourné reflète la suppression
        return model;
    }

    @Override
    public void deleteWishlist(Long wishlistId) {
        if (!wishlistRepository.existsById(wishlistId)) {
            throw new ResourceNotFoundException("Wishlist not found with id: " + wishlistId);
        }
        wishlistRepository.deleteById(wishlistId); // CascadeType.REMOVE sur UserEntity.wishlist devrait être géré si nécessaire
        // orphanRemoval sur WishlistEntity.items gère les items.
    }
}
