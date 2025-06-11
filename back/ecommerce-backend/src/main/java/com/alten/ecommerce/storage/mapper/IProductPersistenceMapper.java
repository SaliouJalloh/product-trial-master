package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.model.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IProductPersistenceMapper extends IAbstractEntityMapper<ProductEntity, Product> {

    Product toModel(ProductEntity entity);

    ProductEntity toEntity(Product model);

}
