package com.example.lab_java_web.service;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import com.example.lab_java_web.common.CategoryType;
import com.example.lab_java_web.domain.Product;
import com.example.lab_java_web.service.exeption.ProductNotFoundException;
import com.example.lab_java_web.service.implementation.ProductServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceImplementationTest {
    private ProductServiceImplementation productService;
    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImplementation();
    }
    @Test
    void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(6, products.size());
    }
    @Test
    void testGetProductById() {
        Product product = productService.getProductById(1L);
        assertNotNull(product);
        assertEquals(1L, product.getId());
    }
    @Test
    void testGetProductByIdNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(100L));
    }
    @Test
    void testCreateProduct() {
        Product newProduct = Product.builder().name("Test product").description("Test product").build();
        Product createdProduct = productService.createProduct(newProduct);
        assertNotNull(createdProduct);
        assertEquals(7L, createdProduct.getId());
    }
    @Test
    void testUpdateProduct() {
        Product newProduct = Product.builder()
                .id(1L)
                .name("Updated Космічне молоко")
                .categories(CategoryType.COSMOMILK)
                .description("Updated description")
                .price(99.99)
                .build();
        Product result = productService.updateProduct(newProduct);
        assertNotNull(result);
        assertEquals(newProduct.getName(), result.getName());
        assertEquals(newProduct.getDescription(), result.getDescription());
        assertEquals(newProduct.getPrice(), result.getPrice());
        assertEquals(newProduct.getCategories(), result.getCategories());
    }
    @Test
    void testUpdateProductNotFound() {
        Product newProduct = Product.builder().id(100L).name("Updated Космічне молоко").build();
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(newProduct));
    }
    @Test
    void testDeleteProductById() {
        productService.deleteProductById(1L);
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }
    @Test
    void testDeleteProductByIdNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(100L));
    }
}