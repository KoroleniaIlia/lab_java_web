package com.example.lab_java_web.service.implementation;

import com.example.lab_java_web.domain.order.OrderDetails;
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
import com.example.lab_java_web.service.exeption.CosmoCatNofFoundException;
import com.example.lab_java_web.service.exeption.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderRepositoryMapper orderRepositoryMapper;
    private final CosmoCatRepository cosmoCatRepository;
    private final ProductRepository productRepository;
    private final ProductRepositoryMapper productRepositoryMapper;
    private final ProductService productService;

    public OrderServiceImplementation(OrderRepository orderRepository, OrderRepositoryMapper orderRepositoryMapper,
                                      CosmoCatRepository cosmoCatRepository, ProductRepository productRepository, ProductRepositoryMapper productRepositoryMapper, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderRepositoryMapper = orderRepositoryMapper;
        this.cosmoCatRepository = cosmoCatRepository;
        this.productRepository = productRepository;
        this.productRepositoryMapper = productRepositoryMapper;
        this.productService = productService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetails> getOrders() {
        return orderRepositoryMapper.toOrderDetails(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetails getOrderByOrderId(UUID orderId) {
        return orderRepositoryMapper.toOrderDetails(orderRepository.findByNaturalId(orderId).orElseThrow(() -> new OrderNotFoundException(orderId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetails> getOrdersByCosmoCatId(UUID cosmoCatId) {
        return orderRepositoryMapper.toOrderDetails(orderRepository.findByCosmoCatCosmoCatId(cosmoCatId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getOrdersIdByCosmoCatId(UUID cosmoCatId) {
        return List.of();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public OrderDetails saveOrder(UUID cosmoCatId, OrderDetails orderDetails) {
        CosmoCatEntity cosmoCat = cosmoCatRepository.findByNaturalId(cosmoCatId)
                .orElseThrow(() -> new CosmoCatNofFoundException(cosmoCatId));
        OrderEntity order = orderRepositoryMapper.toOrderEntity(orderDetails);
        List<OrderEntryEntity> orderEntryEntityList = order.getOrderItems().stream()
                .peek(orderEntryItem -> {
                    UUID productId = orderEntryItem.getProduct().getProductId();
                    ProductEntity productEntity = productRepositoryMapper.toProductEntity(productService.getProductByProductId(productId));
                    orderEntryItem.setProduct(productEntity);
                    orderEntryItem.setOrder(order);
                }).toList();
        order.setOrderItems(orderEntryEntityList);
        order.setCosmoCat(cosmoCat);
        return orderRepositoryMapper.toOrderDetails(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteByNaturalId(orderId);
    }
}
