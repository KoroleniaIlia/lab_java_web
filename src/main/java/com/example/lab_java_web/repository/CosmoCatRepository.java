package com.example.lab_java_web.repository;

import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CosmoCatRepository extends NaturalIdRepository<CosmoCatEntity, UUID> {
}
