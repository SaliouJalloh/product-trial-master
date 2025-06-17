package com.alten.ecommerce.storage.persistence;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alten.ecommerce.storage.entity.ProductEntity;
import com.alten.ecommerce.storage.enums.InventoryStatus;
import com.alten.ecommerce.storage.mapper.IProductPersistenceMapper;
import com.alten.ecommerce.storage.model.Product;
import com.alten.ecommerce.storage.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPersistenceService implements IProductPersistenceService {

  private final ProductRepository productRepository;
  private final IProductPersistenceMapper productPersistenceMapper;

  @Override
  public Product findProductById(Long id) {
    ProductEntity productEntity =
        productRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Product with id {} not found", id);
                  return new EntityNotFoundException("Not product found with id: " + id);
                });
    return productPersistenceMapper.toModel(productEntity);
  }

  @Override
  public List<Product> findAllProducts() {
    List<ProductEntity> customers = productRepository.findAll();
    return customers.stream().map(productPersistenceMapper::toModel).toList();
  }

  @Override
  public Product saveProduct(Product product) {
    ProductEntity productEntity = productPersistenceMapper.toEntity(product);
    ProductEntity savedCustomer = productRepository.save(productEntity);
    return productPersistenceMapper.toModel(savedCustomer);
  }

  @Override
  public Product updateProduct(Product product, Long id) {
    ProductEntity updateCustomer =
        productRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Product with id {} not updated, because it was not found.", id);
                  return new EntityNotFoundException("Not product found with id: " + id);
                });
    updateCustomer.setCode(product.getCode());
    updateCustomer.setName(product.getName());
    updateCustomer.setPrice(product.getPrice());
    updateCustomer.setQuantity(product.getQuantity());
    updateCustomer.setCategory(product.getCategory());
    updateCustomer.setDescription(product.getDescription());
    updateCustomer.setInventoryStatus(InventoryStatus.INSTOCK);
    updateCustomer.setUpdatedAt(product.getUpdatedAt());
    productRepository.save(updateCustomer);
    return productPersistenceMapper.toModel(updateCustomer);
  }

  @Override
  public void deleteProductById(Long id) {
    ProductEntity product =
        productRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Product with id {} not deleted, because it was not found.", id);
                  return new EntityNotFoundException("Product not found with id: " + id);
                });
    productRepository.deleteById(product.getId());
  }
}
