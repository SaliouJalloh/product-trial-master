package com.alten.ecommerce.storage.mapper;

import org.mapstruct.MappingTarget;

public interface IAbstractEntityMapper<M, E> {

    /**
     * Convertir une entité en modèle
     */
    M toModel(E entity);

    /**
     * Convertir un modèle en entité
     */
    E toEntity(M model);

    M populateModel(@MappingTarget M model, E entity);
}