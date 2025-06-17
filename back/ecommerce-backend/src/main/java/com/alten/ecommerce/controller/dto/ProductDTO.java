package com.alten.ecommerce.controller.dto;

public record ProductDTO(
    Long id,
    String code,
    String name,
    String description,
    String image,
    String category,
    Double price,
    Integer quantity) {}
