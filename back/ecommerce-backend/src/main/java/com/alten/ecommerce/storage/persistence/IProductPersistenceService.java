package com.alten.ecommerce.storage.persistence;

import java.util.List;

import com.alten.ecommerce.storage.model.Product;

public interface IProductPersistenceService {
  Product findProductById(Long id);

  List<Product> findAllProducts();

  Product saveProduct(Product product);

  Product updateProduct(Product product, Long id);

  void deleteProductById(Long id);
}
