package com.example.lab_java_web.repository.mapper;

import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderRepositoryMapper.class)
public interface CosmoCatRepositoryMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cosmoCatId", source = "cosmoCatId")
    @Mapping(target = "catCosmoName", source = "catCosmoName")
    @Mapping(target = "realName", source = "realName")
    @Mapping(target = "catEmail", source = "catEmail")
    @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderDetails")
    CosmoCatDetails toCosmoCatDetails(CosmoCatEntity cosmoCatEntity);

    List<CosmoCatDetails> toCosmoCatDetails(List<CosmoCatEntity> cosmoCatEntities);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cosmoCatId", source = "cosmoCatId")
    @Mapping(target = "catCosmoName", source = "catCosmoName")
    @Mapping(target = "realName", source = "realName")
    @Mapping(target = "catEmail", source = "catEmail")
    @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderEntity")
    CosmoCatEntity toCosmoCatEntity(CosmoCatDetails cosmoCatDetails);

    List<CosmoCatEntity> toCosmoCatEntity(List<CosmoCatDetails> cosmoCatDetails);
}