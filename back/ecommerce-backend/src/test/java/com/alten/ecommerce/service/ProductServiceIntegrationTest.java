package com.alten.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.persistence.IProductPersistenceService;
import com.alten.ecommerce.tools.DataProviderTest;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

  @Autowired private ProductService productService;

  @Autowired private IProductPersistenceService productPersistenceService;

  @BeforeEach
  void setUp() {
    // Clean up all products before each test
    productPersistenceService
        .findAllProducts()
        .forEach(p -> productPersistenceService.deleteProductById(p.getId()));
  }

  @Test
  @DisplayName("✅ Création d'un produit")
  void testCreateProduct() {
    Product product = DataProviderTest.buildProduct();
    Product saved = productService.createProduct(product);
    assertNotNull(saved.getId());
    assertEquals(product.getName(), saved.getName());
  }

  @Test
  @DisplayName("✅ Récupération de tous les produits")
  void testGetAllProducts() {
    Product product1 = productService.createProduct(DataProviderTest.buildProduct());
    Product product2 =
        Product.builder()
            .name("Other")
            .code("222")
            .quantity(10)
            .description("Product Description")
            .price(100.0)
            .build();
    product2 = productService.createProduct(product2);
    List<Product> products = productService.getAllProducts();
    assertTrue(products.size() >= 2);
  }

  @Test
  @DisplayName("✅ Récupération d'un produit par ID")
  void testGetProductById() {
    Product product = productService.createProduct(DataProviderTest.buildProduct());
    Product found = productService.getProductById(product.getId());
    assertNotNull(found);
    assertEquals(product.getId(), found.getId());
  }

  @Test
  @DisplayName("✅ Mise à jour d'un produit")
  void testUpdateProduct() {
    Product product = productService.createProduct(DataProviderTest.buildProduct());
    Product updated =
        Product.builder()
            .id(product.getId())
            .name("Updated Name")
            .code(product.getCode())
            .quantity(product.getQuantity())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    Product result = productService.updateProduct(updated, product.getId());
    assertEquals("Updated Name", result.getName());
  }

  @Test
  @DisplayName("✅ Suppression d'un produit")
  void testDeleteProduct() {
    Product product = productService.createProduct(DataProviderTest.buildProduct());
    productService.deleteProduct(product.getId());
    assertThrows(Exception.class, () -> productService.getProductById(product.getId()));
  }
}
