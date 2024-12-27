package com.example.lab_java_web.web;

import com.example.lab_java_web.AbstractIt;
import com.example.lab_java_web.common.Categories;
import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.dto.product.ProductDTO;
import com.example.lab_java_web.featuretoggle.FeatureToggleExtension;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.lab_java_web.featuretoggle.annotation.EnabledFeatureToggle;
import com.example.lab_java_web.repository.entity.ProductEntity;
import com.example.lab_java_web.repository.mapper.ProductRepositoryMapper;
import com.example.lab_java_web.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
@ExtendWith(FeatureToggleExtension.class)
public class ProductControllerIT extends AbstractIt {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepositoryMapper productRepositoryMapper;

    @BeforeEach
    void setUp() {
        reset(productService);
    }

    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGetAllProducts() {
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGetAllProductsFeatureToggleDisabled() {
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldGetProductByProductId() {
        ProductEntity productEntity = createProduct();
        mockMvc.perform(get("/api/v1/products/{productId}", productEntity.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldGetProductByProductIdNotFound() {
        mockMvc.perform(get("/api/v1/products/{productId}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldCreateProduct() {
        ProductDTO productDTO = new ProductDTO().toBuilder()
                .name("Test")
                .description("Test")
                .price(99.99)
                .categories("Other")
                .build();
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldCreateProductCategoryValidationError() {
        ProductDTO productDTO = new ProductDTO().toBuilder()
                .name("Test")
                .description("Test")
                .price(99.99)
                .categories("Wrong category")
                .build();
        mockMvc.perform(post("/api/v1/products")
                        .content(objectMapper.writeValueAsString(productDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldUpdateProduct() {
        ProductEntity productEntity = createProduct();
        ProductDTO productDTO = new ProductDTO().toBuilder()
                .name("Test")
                .description("Test")
                .price(199.99)
                .categories("Accessories")
                .build();
        mockMvc.perform(put("/api/v1/products/{productId}", productEntity.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldUpdateProductValidationError() {
        ProductEntity productEntity = createProduct();
        ProductDTO productDTO = new ProductDTO().toBuilder()
                .name("Test")
                .description("Test")
                .price(259.99)
                .categories("Wrong category")
                .build();
        System.out.println(objectMapper.writeValueAsString(productDTO));
        mockMvc.perform(put("/api/v1/products/{productId}", productEntity.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldUpdateProductNotFound() {
        ProductDTO productDTO = new ProductDTO().toBuilder()
                .name("Test")
                .description("Test")
                .price(259.99)
                .categories("Accessories")
                .build();
        System.out.println(objectMapper.writeValueAsString(productDTO));
        mockMvc.perform(put("/api/v1/products/{productId}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldDeleteProduct() {
        ProductEntity productEntity = createProduct();
        mockMvc.perform(delete("/api/v1/products/{productId}", productEntity.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/products/{productId}", productEntity.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private ProductEntity createProduct() {
        return productRepositoryMapper.toProductEntity(productService.saveProduct(ProductDetails.builder()
                .productId(UUID.randomUUID())
                .name("Test Product")
                .description("Product Descr")
                .price(59.99)
                .categories(Categories.OTHER)
                .build()));
    }
}