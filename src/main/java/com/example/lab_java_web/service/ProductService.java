package com.example.lab_java_web.service;

import com.example.lab_java_web.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductById(Long id);
    void cleanProductList();
}