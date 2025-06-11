package com.alten.ecommerce.storage.mapper;

import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.model.Product; // Modèle Product
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IProductStorageMapper {

    // Gérer la conversion de Double (entité) en BigDecimal (modèle) pour le prix
    @Mapping(source = "price", target = "price")
    Product toModel(ProductEntity entity, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "price", target = "price")
    ProductEntity toEntity(Product model, @Context CycleAvoidingMappingContext context);

    // Méthodes de conversion pour Double <-> BigDecimal si MapStruct ne le fait pas automatiquement
    // ou si une logique spécifique est requise.
    // MapStruct devrait gérer cela nativement si les types sont Double et BigDecimal.
    // Si ce n'est pas le cas, on ajouterait :
    // default BigDecimal map(Double value) { return value != null ? BigDecimal.valueOf(value) : null; }
    // default Double map(BigDecimal value) { return value != null ? value.doubleValue() : null; }
}
