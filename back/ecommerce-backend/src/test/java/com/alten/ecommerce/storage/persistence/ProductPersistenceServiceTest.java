package com.alten.ecommerce.storage.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.alten.ecommerce.storage.mapper.IProductPersistenceMapperImpl;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.repository.ProductRepository;
import com.alten.ecommerce.tools.DataProviderTest;

@DataJpaTest
@Import({ProductPersistenceService.class, IProductPersistenceMapperImpl.class})
class ProductPersistenceServiceTest {

  @Autowired private ProductRepository productRepository;

  @Autowired private IProductPersistenceService productPersistenceService;

  @BeforeEach
  void setUp() {
    productRepository.deleteAll();
    productRepository.flush();
  }

  @Test
  void findProductById_success() {
    // When
    Product product = productPersistenceService.saveProduct(DataProviderTest.buildProduct());
    Product savedProduct = productPersistenceService.findProductById(product.getId());

    // Then
    assertThat(product).isNotNull();
    assertThat(savedProduct)
        .usingRecursiveComparison()
        .ignoringFields("id", "createdAt", "lastModifiedAt")
        .isEqualTo(product);
  }
}
