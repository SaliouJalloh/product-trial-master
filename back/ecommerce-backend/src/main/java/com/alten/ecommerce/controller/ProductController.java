package com.alten.ecommerce.controller;

import com.alten.ecommerce.controller.dto.ProductDTO;
import com.alten.ecommerce.controller.mapper.IControllerMapper;
import com.alten.ecommerce.service.IProductService;
import com.alten.ecommerce.storage.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("${application.frontend.basePath}")
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final IControllerMapper controllerMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> allCustomers = productService.getAllProducts();
        return allCustomers.stream().map(controllerMapper::toProductDTO).toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return controllerMapper.toProductDTO(productService.getProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ProductDTO createProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return controllerMapper.toProductDTO(savedProduct);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ProductDTO updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        Product updatedCustomer = productService.updateProduct(product, id);
        return controllerMapper.toProductDTO(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}