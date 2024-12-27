package com.example.lab_java_web.service;

import com.example.lab_java_web.domain.ProductDetails;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDetails> getAllProducts();


    ProductDetails getProductByProductId(UUID productId);

    ProductDetails saveProduct(ProductDetails productId);

    ProductDetails saveProduct(UUID productId, ProductDetails productDetails);

    void deleteProduct(UUID productId);
    void cleanProductList();
}