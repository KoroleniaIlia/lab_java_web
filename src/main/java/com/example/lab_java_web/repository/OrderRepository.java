package com.example.lab_java_web.repository;

import com.example.lab_java_web.repository.entity.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCosmoCatCosmoCatId(UUID cosmoCatId);
}