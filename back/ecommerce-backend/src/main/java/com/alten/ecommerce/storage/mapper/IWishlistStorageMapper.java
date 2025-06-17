package com.alten.ecommerce.storage.mapper;

import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.ecommerce.storage.entity.WishlistEntity;
import com.alten.ecommerce.storage.model.Wishlist;

@Mapper(
    componentModel = "spring",
    uses = {IUserStorageMapper.class, IWishlistItemStorageMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IWishlistStorageMapper {

  @Mapping(source = "user", target = "user")
  @Mapping(source = "items", target = "items")
  Wishlist toModel(
      WishlistEntity entity,
      @Context CycleAvoidingMappingContext context); // Type de retour mis à jour

  // Pour la conversion modèle -> entité, l'ID de l'utilisateur dans le modèle User devrait suffire.
  // MapStruct devrait pouvoir mapper model.user.id vers entity.user.id si les structures
  // correspondent.
  // Sinon, une méthode @AfterMapping ou un mapping explicite serait nécessaire.
  // Ici, on s'attend à ce que le service fournisse une UserEntity si nécessaire, ou que l'ID soit
  // suffisant.
  @Mapping(source = "user.id", target = "user.id")
  @Mapping(target = "items", ignore = true) // Gérer les items séparément
  WishlistEntity toEntity(
      Wishlist model, @Context CycleAvoidingMappingContext context); // Type de paramètre mis à jour

  // Cette méthode mapUser n'est plus strictement nécessaire si le mapping user.id -> user.id
  // fonctionne
  // ou si le service gère la liaison de UserEntity.
  // default UserEntity mapUser(Long userId, @Context CycleAvoidingMappingContext context) { ... }
}
