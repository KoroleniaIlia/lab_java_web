package com.example.lab_java_web.web;

import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.dto.product.ProductDTO;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.FeatureToggle;
import com.example.lab_java_web.service.ProductService;
import com.example.lab_java_web.service.mapper.ProductServiceMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductServiceMapper productServiceMapper;

    public ProductController(ProductService productService, ProductServiceMapper productServiceMapper) {
        this.productService = productService;
        this.productServiceMapper = productServiceMapper;
    }

    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productServiceMapper.toProductDto(productService.getAllProducts()));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(productServiceMapper.toProductDto(productService.getProductByProductId(productId)));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productServiceMapper.toProductDto(
                productService.saveProduct(productServiceMapper.toProductDetails(productDTO))));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID productId, @RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productServiceMapper.toProductDto(productService
                .saveProduct(productId, productServiceMapper.toProductDetails(productDTO))));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}