package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.UserEntity;
import com.alten.ecommerce.storage.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUserPersistenceMapper extends IAbstractEntityMapper<User, UserEntity> {

    User toModel(UserEntity userEntity);

    UserEntity toEntity(User user);
}