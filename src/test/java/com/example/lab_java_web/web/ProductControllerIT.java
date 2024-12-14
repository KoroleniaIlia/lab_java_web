package com.example.lab_java_web.web;


import com.example.lab_java_web.common.CategoryType;
import com.example.lab_java_web.domain.Product;
import com.example.lab_java_web.dto.ProductDTO;
import com.example.lab_java_web.service.ProductService;
import com.example.lab_java_web.service.exeption.ProductNotFoundException;
import com.example.lab_java_web.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
public class ProductControllerIT {
    private ProductDTO productDTO;
    private Product mockProduct;
    private final Long PRODUCT_ID = 1L;
    private final List<ProductDTO> productListDto = buildProductDTOList();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productService.cleanProductList();
        mockProduct = Product.builder()
                .id(1L)
                .name("Космічне молоко")
                .categories(CategoryType.COSMOMILK)
                .description("Молоко космічної корови")
                .price(59.99)
                .build();
        productDTO = ProductDTO.builder()
                .name("Космічне молоко")
                .price(59.99)
                .categories("CosmoMilk")
                .description("Молоко космічної корови")
                .build();
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(buildProductList());
        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(productListDto.size())))
                .andExpect(jsonPath("$[0].name").value(productMapper.toProduct(productListDto.get(0)).getName()))
                .andExpect(jsonPath("$[0].category").value((productMapper.toProduct(productListDto.get(0)).getCategories().getDisplayName())))
                .andExpect(jsonPath("$[1].name").value(productMapper.toProduct(productListDto.get(1)).getName()))
                .andExpect(jsonPath("$[1].category").value(productMapper.toProduct(productListDto.get(1)).getCategories().getDisplayName()))
                .andExpect(jsonPath("$[2].name").value(productMapper.toProduct(productListDto.get(2)).getName()))
                .andExpect(jsonPath("$[2].category").value(productMapper.toProduct(productListDto.get(2)).getCategories().getDisplayName()));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenReturn(mockProduct);
        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.category").value(productDTO.getCategories()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(productDTO.getDescription()));
    }

    @Test
    void shouldThrowProductNotFoundException() throws Exception {
        when(productService.getProductById(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/api/v1/products/{id}", 10000L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Product Not Found"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(mockProduct);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.category").value(productDTO.getCategories()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(productDTO.getDescription()));
    }

    @ParameterizedTest
    @MethodSource("invalidProductDTOs")
    void shouldThrowValidationErrorCreateProduct(ProductDTO productDTO, String fieldName, String message) throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value(fieldName))
                .andExpect(jsonPath("$.invalidParams[0].reason").value(message));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProductById(PRODUCT_ID);
        mockMvc.perform(delete("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Product was successfully deleted"));
    }

    @Test
    void shouldThrowErrorProductNotFoundDelete() throws Exception {
        doThrow(ProductNotFoundException.class).when(productService).deleteProductById(any());
        mockMvc.perform(delete("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDTO updatedDTO = ProductDTO.builder()
                .name("Updated Космічне молоко")
                .price(59.99)
                .categories("CosmoMild")
                .description("Молоко космічної корови")
                .build();
        Product updatedProduct = productMapper.toProduct(updatedDTO);
        when(productService.updateProduct(any(Product.class))).thenReturn(updatedProduct);
        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedDTO.getName()))
                .andExpect(jsonPath("$.category").value(updatedDTO.getCategories()))
                .andExpect(jsonPath("$.price").value(updatedDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(updatedDTO.getDescription()));
    }

    @ParameterizedTest
    @MethodSource("invalidProductDTOs")
    void shouldThrowValidationErrorUpdateProduct(ProductDTO productDTO, String fieldName, String message) throws Exception {
        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value(fieldName))
                .andExpect(jsonPath("$.invalidParams[0].reason").value(message));
    }

    @Test
    void shouldThrowProductNotFoundExceptionUpdateProduct() throws Exception {
        ProductDTO updatedDTO = ProductDTO.builder()
                .name("Updated Космічне молоко")
                .price(59.99)
                .categories("CosmoMilk")
                .description("Неіснуюче молоко космічної корови")
                .build();
        when(productService.updateProduct(any(Product.class))).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(put("/api/v1/products/{id}", 10000L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Product Not Found"));
    }

    private static List<ProductDTO> buildProductDTOList() {
        List<ProductDTO> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductDTO.builder()
                .name("Космічне молоко")
                .categories("CosmoMilk")
                .description("Молоко космічної корови")
                .price(59.99)
                .build());
        listOfProducts.add(ProductDTO.builder()
                .name("Котячі космічні ниткі")
                .categories("Threads")
                .description("Спеціальні ниткі, чудове заспокійлеве для космічних котів")
                .price(89.99)
                .build());
        listOfProducts.add(ProductDTO.builder()
                .name("Космічна машина")
                .categories("CosmoCar")
                .description("Чудова річ для подорожів у космосі")
                .price(2000.99)
                .build());
        return listOfProducts;
    }

    private List<Product> buildProductList() {
        return buildProductDTOList().stream()
                .map(dto -> productMapper.toProduct(dto)).collect(Collectors.toList());
    }

    private static Stream<Arguments> invalidProductDTOs() {
        return Stream.of(
                Arguments.of(ProductDTO.builder()
                                .name("Котячі космічні ниткі")
                                .categories("Wrong category")
                                .price(850.0)
                                .description("Котячі космічні ниткі з неправильною категорією")
                                .build(), "category",
                        "Invalid Space Category it must be: CosmoMilk, Threads, CosmoCar, CosmoToys, Games, or Other if you didn't find right category"),
                Arguments.of(ProductDTO.builder()
                                .name("Котячі космічні ниткі")
                                .categories("Threads")
                                .price(850.0)
                                .description("Котячі космічні ниткі з неправильною категорією")
                                .build(), "name",
                        "Name is required"),
                Arguments.of(ProductDTO.builder()
                                .name("Котячі космічні ниткі")
                                .categories("Threads")
                                .price(-850.0)
                                .description("Котячі космічні ниткі з неправильною категорією")
                                .build(), "price",
                        "Price can't be negative or zero"),
                Arguments.of(ProductDTO.builder()
                                .name("Котячі космічні ниткі")
                                .categories("Threads")
                                .price(850.0)
                                .description("")
                                .build(), "description",
                        "Description is required")
        );
    }
}