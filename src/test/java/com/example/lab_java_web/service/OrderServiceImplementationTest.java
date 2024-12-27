package com.example.lab_java_web.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import com.example.lab_java_web.config.MappersTestConfiguration;
import com.example.lab_java_web.domain.order.OrderDetails;
import com.example.lab_java_web.repository.CosmoCatRepository;
import com.example.lab_java_web.repository.OrderRepository;
import com.example.lab_java_web.repository.ProductRepository;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import com.example.lab_java_web.repository.entity.OrderEntity;
import com.example.lab_java_web.repository.entity.ProductEntity;
import com.example.lab_java_web.repository.mapper.OrderRepositoryMapper;
import com.example.lab_java_web.repository.mapper.ProductRepositoryMapper;
import com.example.lab_java_web.service.exeption.OrderNotFoundException;
import com.example.lab_java_web.service.implementation.OrderServiceImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {OrderServiceImplementation.class})
@Import(MappersTestConfiguration.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Order Service Test")
public class OrderServiceImplementationTest {
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductRepositoryMapper productRepositoryMapper;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CosmoCatRepository cosmoCatRepository;
    @MockBean
    private OrderRepositoryMapper orderRepositoryMapper;
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private OrderServiceImplementation orderService;
    @Test
    void testGetAllOrders() {
        List<OrderEntity> entities = List.of(new OrderEntity());
        List<OrderDetails> details = List.of(new OrderDetails());
        when(orderRepository.findAll()).thenReturn(entities);
        when(orderRepositoryMapper.toOrderDetails(entities)).thenReturn(details);
        List<OrderDetails> result = orderService.getOrders();
        assertEquals(details, result);
    }
    @Test
    void testGetOrderByOrderId() {
        UUID orderId = UUID.randomUUID();
        OrderEntity entity = new OrderEntity();
        OrderDetails details = new OrderDetails();
        when(orderRepository.findByNaturalId(orderId)).thenReturn(Optional.of(entity));
        when(orderRepositoryMapper.toOrderDetails(entity)).thenReturn(details);
        OrderDetails result = orderService.getOrderByOrderId(orderId);
        assertEquals(details, result);
    }
    @Test
    void testGetOrderByOrderIdNotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findByNaturalId(orderId)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderByOrderId(orderId));
    }
    @Test
    void testSaveOrder() {
        UUID cosmoCatId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderDetails.setOrderItems(new ArrayList<>());
        CosmoCatEntity cosmoCatEntity = new CosmoCatEntity();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setCosmoCat(cosmoCatEntity);
        orderEntity.setOrderItems(new ArrayList<>());
        when(cosmoCatRepository.findByNaturalId(cosmoCatId)).thenReturn(Optional.of(cosmoCatEntity));
        when(productRepository.findByNaturalId(productId)).thenReturn(Optional.of(new ProductEntity()));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderRepositoryMapper.toOrderDetails(any(OrderEntity.class))).thenReturn(orderDetails);
        when(orderRepositoryMapper.toOrderEntity(any(OrderDetails.class))).thenReturn(orderEntity);
        OrderDetails result = orderService.saveOrder(cosmoCatId, orderDetails);
        assertEquals(orderDetails, result);
    }
    @Test
    void testDeleteOrderById() {
        UUID orderId = UUID.randomUUID();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        when(orderRepository.findByNaturalId(orderId)).thenReturn(Optional.of(new OrderEntity()));
        doNothing().when(orderRepository).deleteByNaturalId(orderId);
        orderService.deleteOrder(orderId);
        verify(orderRepository, times(1)).deleteByNaturalId(orderId);
    }
}
