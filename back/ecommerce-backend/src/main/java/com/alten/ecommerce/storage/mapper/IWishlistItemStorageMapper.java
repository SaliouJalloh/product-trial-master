package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.WishlistItemEntity;
import com.alten.ecommerce.storage.model.WishlistItem; // Utilise le modèle de storage.model
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {IProductStorageMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IWishlistItemStorageMapper {

    @Mapping(source = "product", target = "product")
    WishlistItem toModel(WishlistItemEntity entity, @Context CycleAvoidingMappingContext context); // Type de retour mis à jour

    @Mapping(source = "product.id", target = "product.id")
    @Mapping(target = "wishlist", ignore = true)
    WishlistItemEntity toEntity(WishlistItem model, @Context CycleAvoidingMappingContext context); // Type de paramètre mis à jour

    // Cette méthode mapProduct n'est plus strictement nécessaire si le mapping product.id -> product.id fonctionne
    // ou si le service gère la liaison de ProductEntity.
    // default ProductEntity mapProduct(Long productId, @Context CycleAvoidingMappingContext context) { ... }
}
