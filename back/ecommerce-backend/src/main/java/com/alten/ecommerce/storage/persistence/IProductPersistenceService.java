package com.alten.ecommerce.storage.persistence;

import com.alten.ecommerce.storage.model.Product;

import java.util.List;

public interface IProductPersistenceService {
    Product findProductById(Long id);

    List<Product> findAllProducts();

    Product saveProduct(Product product);

    Product updateProduct(Product product, Long id);

    void deleteProductById(Long id);
}
