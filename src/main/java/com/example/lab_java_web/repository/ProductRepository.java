package com.example.lab_java_web.repository;

import com.example.lab_java_web.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
}
