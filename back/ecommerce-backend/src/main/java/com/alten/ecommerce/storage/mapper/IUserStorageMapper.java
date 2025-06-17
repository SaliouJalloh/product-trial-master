package com.alten.ecommerce.storage.mapper;

import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUserStorageMapper {

  // Pour l'instant, un mapping simple. Peut être étendu si User et UserEntity divergent plus.
  // @Mapping(target = "password", ignore = true) // Souvent, on ne mappe pas le mot de passe vers
  // le modèle
  User toModel(UserEntity entity, @Context CycleAvoidingMappingContext context);

  UserEntity toEntity(User model, @Context CycleAvoidingMappingContext context);
}
