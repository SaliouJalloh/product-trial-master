package com.alten.ecommerce.service;

import java.util.List;

import com.alten.ecommerce.storage.model.Product;

public interface IProductService {
  Product createProduct(Product customer);

  List<Product> getAllProducts();

  Product getProductById(Long id);

  Product updateProduct(Product product, Long id);

  void deleteProduct(Long id);
}
