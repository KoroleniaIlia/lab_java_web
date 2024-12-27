package com.example.lab_java_web.service;

import com.example.lab_java_web.domain.order.OrderDetails;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDetails> getOrders();

    List<OrderDetails> getOrdersByCosmoCatId(UUID cosmoCatId);

    OrderDetails getOrderByOrderId(UUID orderId);

    List<UUID> getOrdersIdByCosmoCatId(UUID cosmoCatId);

    OrderDetails saveOrder(UUID cosmoCatId, OrderDetails orderDetails);

    void deleteOrder(UUID orderId);
}