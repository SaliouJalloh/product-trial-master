package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.CartEntity;
import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.Cart;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ICartItemStorageMapper.class})
public interface ICartStorageMapper {

    ICartStorageMapper INSTANCE = Mappers.getMapper(ICartStorageMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "items", target = "items")
        // Relies on ICartItemStorageMapper
    Cart entityToModel(CartEntity entity);

    @Mapping(source = "userId", target = "user", qualifiedByName = "userIdToUserEntity")
    @Mapping(source = "items", target = "items")
        // Relies on ICartItemStorageMapper
    CartEntity modelToEntity(Cart model, @Context UserEntity user);

    @Named("userIdToUserEntity")
    default UserEntity userIdToUserEntity(Long userId, @Context UserEntity userContext) {
        if (userContext != null && userContext.getId().equals(userId)) {
            return userContext;
        }
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }
}
