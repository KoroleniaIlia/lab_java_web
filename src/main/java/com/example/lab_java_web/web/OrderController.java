package com.example.lab_java_web.web;

import com.example.lab_java_web.dto.order.OrderDto;
import com.example.lab_java_web.service.OrderService;
import com.example.lab_java_web.service.mapper.OrderServiceMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderServiceMapper orderServiceMapper;

    public OrderController(OrderService orderService, OrderServiceMapper orderServiceMapper) {
        this.orderService = orderService;
        this.orderServiceMapper = orderServiceMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderServiceMapper.toOrderDto(orderService.getOrders()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderServiceMapper.toOrderDto(orderService.getOrderByOrderId(orderId)));
    }

    @GetMapping("/cosmo-cat/{cosmoCatId}")
    public ResponseEntity<List<OrderDto>> getCosmicCatOrders(@PathVariable UUID cosmoCatId) {
        return ResponseEntity.ok(orderServiceMapper.toOrderDto(orderService.getOrdersByCosmoCatId(cosmoCatId)));
    }

    @PostMapping("/cosmo-cat/{cosmoCatId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto, @PathVariable UUID cosmoCatId) {
        return ResponseEntity.ok(orderServiceMapper.toOrderDto(orderService
                .saveOrder(cosmoCatId, orderServiceMapper.toOrderDetails(orderDto))));

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}