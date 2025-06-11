package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.CartItemEntity;
import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.model.CartItem;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ICartItemStorageMapper {

    ICartItemStorageMapper INSTANCE = Mappers.getMapper(ICartItemStorageMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.image", target = "productImage")
    @Mapping(source = "product.price", target = "productPrice", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "itemTotalPrice", ignore = true)
    CartItem entityToModel(CartItemEntity entity);

    @Mapping(source = "productId", target = "product", qualifiedByName = "productIdToProductEntity")
    @Mapping(target = "cart", ignore = true)
    CartItemEntity modelToEntity(CartItem model, @Context ProductEntity product);

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    @Named("productIdToProductEntity")
    default ProductEntity productIdToProductEntity(Long productId, @Context ProductEntity productContext) {
        if (productContext != null && productContext.getId().equals(productId)) {
            return productContext;
        }
        if (productId == null) return null;
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        return product;
    }
}
