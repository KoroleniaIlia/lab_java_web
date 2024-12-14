package com.example.lab_java_web.domain.order;

import com.example.lab_java_web.domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    Product product;
    Integer quantity;
}