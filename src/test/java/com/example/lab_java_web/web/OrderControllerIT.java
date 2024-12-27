package com.example.lab_java_web.web;

import com.example.lab_java_web.AbstractIt;
import com.example.lab_java_web.common.Categories;
import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.domain.order.OrderDetails;
import com.example.lab_java_web.domain.order.OrderEntryDetails;
import com.example.lab_java_web.dto.order.OrderDto;
import com.example.lab_java_web.dto.order.OrderEntryDto;
import com.example.lab_java_web.repository.CosmoCatRepository;
import com.example.lab_java_web.repository.OrderRepository;
import com.example.lab_java_web.repository.ProductRepository;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import com.example.lab_java_web.repository.entity.OrderEntity;
import com.example.lab_java_web.repository.entity.OrderEntryEntity;
import com.example.lab_java_web.repository.entity.ProductEntity;
import com.example.lab_java_web.repository.mapper.OrderRepositoryMapper;
import com.example.lab_java_web.repository.mapper.ProductRepositoryMapper;
import com.example.lab_java_web.service.OrderService;
import com.example.lab_java_web.service.ProductService;
import com.example.lab_java_web.service.mapper.ProductServiceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
public class OrderControllerIT extends AbstractIt {
    private final UUID COSMO_CAT_ID = UUID.randomUUID();
    private final UUID PRODUCT_ID = UUID.randomUUID();
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepositoryMapper orderRepositoryMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRepositoryMapper productRepositoryMapper;
    @Autowired
    private CosmoCatRepository cosmoCatRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductServiceMapper productServiceMapper;

    @BeforeEach
    void setUp() {
        reset(orderService);
        orderRepository.deleteAll();
        productRepository.deleteAll();
        cosmoCatRepository.deleteAll();
        createCosmoCatAndProduct();
    }

    @Test
    @SneakyThrows
    void shouldGetAllOrders() {
        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldGetOrder() {
        OrderEntity orderEntity = createOrder();
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderEntity.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldGetOrderNotFound() {
        OrderEntity orderEntity = createOrder();
        mockMvc.perform(get("/api/v1/orders/{orderId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldCreateOrder() {
        OrderDto orderDto = OrderDto.builder()
                .orderItems(List.of(
                        OrderEntryDto.builder()
                                .quantity(2)
                                .product(productServiceMapper.toProductDto(productService.getProductByProductId(PRODUCT_ID)))
                                .build()
                ))
                .build();
        mockMvc.perform(post("/api/v1/orders/cosmo-cat/{cosmoCatId}", COSMO_CAT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldCreateOrderCosmoCatNotFound() {
        OrderDto orderDto = OrderDto.builder()
                .orderItems(List.of(
                        OrderEntryDto.builder()
                                .quantity(2)
                                .product(productServiceMapper.toProductDto(productService.getProductByProductId(PRODUCT_ID)))
                                .build()
                ))
                .build();
        mockMvc.perform(post("/api/v1/orders/cosmo-cat/{cosmoCatId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldDeleteOrder() {
        OrderEntity orderEntity = createOrder();
        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderEntity.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderEntity.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private OrderEntity createOrder() {
        ProductEntity productEntity = productRepositoryMapper.toProductEntity(productService.getProductByProductId(PRODUCT_ID));
        OrderEntryEntity orderEntry = orderRepositoryMapper.toOrderEntryEntity(OrderEntryDetails.builder()
                .quantity(4)
                .product(productRepositoryMapper.toProductDetails(productEntity))
                .build());
        return orderRepositoryMapper.toOrderEntity(orderService.saveOrder(COSMO_CAT_ID, OrderDetails.builder()
                .orderItems(List.of(orderRepositoryMapper.toOrderEntryDetails(orderEntry)))
                .orderId(UUID.randomUUID())
                .build()));
    }

    private void createCosmoCatAndProduct() {
        cosmoCatRepository.save(CosmoCatEntity.builder()
                .cosmoCatId(COSMO_CAT_ID)
                .catCosmoName("Mars")
                .realName("Marsikіt")
                .catEmail("Marsikit@catmail.com")
                .realName("Mars Cat")
                .build());
        productRepository.save(productRepositoryMapper.toProductEntity(ProductDetails.builder()
                .productId(PRODUCT_ID)
                .name("Test Product")
                .description("Test Description")
                .price(59.99)
                .categories(Categories.OTHER)
                .build()));
    }
}
