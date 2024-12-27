package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.domain.order.OrderDetails;
import com.example.lab_java_web.dto.cat.CosmoCatsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CosmoCatServiceMapper {
    @Mapping(source = "cosmoCatId", target = "cosmoCatId")
    @Mapping(source = "catCosmoName", target = "catCosmoName")
    @Mapping(source = "realName", target = "realName")
    @Mapping(source = "catEmail", target = "catEmail")
    @Mapping(source = "orders", target = "orders", qualifiedByName = "toOrderUUID")
    CosmoCatsDTO toCosmoCatDTO(CosmoCatDetails cosmoCatDetails);

    List<CosmoCatsDTO> toCosmoCatDTO(List<CosmoCatDetails> cosmoCatDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cosmoCatId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "catCosmoName", source = "catCosmoName")
    @Mapping(source = "realName", target = "realName")
    @Mapping(source = "catEmail", target = "catEmail")
    @Mapping(target = "orders", ignore = true)
    CosmoCatDetails toCosmoCatDetails(CosmoCatsDTO cosmoCatDto);

    List<CosmoCatDetails> toCosmoCatDetails(List<CosmoCatsDTO> cosmoCatDTO);

    @Named("toOrderUUID")
    default List<UUID> toOrderUUID(List<OrderDetails> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(OrderDetails::getOrderId)
                .toList();
    }
}