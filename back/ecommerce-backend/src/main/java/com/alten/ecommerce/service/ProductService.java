package com.alten.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.persistence.IProductPersistenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

  private final IProductPersistenceService productPersistenceService;

  @Override
  public List<Product> getAllProducts() {
    return productPersistenceService.findAllProducts();
  }

  @Override
  public Product getProductById(Long id) {
    return productPersistenceService.findProductById(id);
  }

  @Override
  public Product createProduct(Product product) {
    log.info("Saving product: {}", product);
    return productPersistenceService.saveProduct(product);
  }

  @Override
  public Product updateProduct(Product product, Long id) {
    return productPersistenceService.updateProduct(product, id);
  }

  @Override
  public void deleteProduct(Long id) {
    productPersistenceService.deleteProductById(id);
  }
}
