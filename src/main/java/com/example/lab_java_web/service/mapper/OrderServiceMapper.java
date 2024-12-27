package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.domain.order.OrderDetails;
import com.example.lab_java_web.domain.order.OrderEntryDetails;
import com.example.lab_java_web.dto.order.OrderDto;
import com.example.lab_java_web.dto.order.OrderEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductServiceMapper.class})
public interface OrderServiceMapper {
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderEntryDto")
    OrderDto toOrderDto(OrderDetails orderDetails);

    List<OrderDto> toOrderDto(List<OrderDetails> orderDetailsList);

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductDto")
    @Named("toOrderEntryDto")
    OrderEntryDto toOrderEntryDto(OrderEntryDetails orderEntryDetails);

    @Named("toOrderEntryDto")
    List<OrderEntryDto> toOrderEntryDto(List<OrderEntryDetails> orderEntryDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderEntryDetails")
    OrderDetails toOrderDetails(OrderDto orderDto);

    List<OrderDetails> toOrderDetails(List<OrderDto> orderDtoList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductDetailsWithReference")
    @Named("toOrderEntryDetails")
    OrderEntryDetails toOrderEntryDetails(OrderEntryDto orderEntryDto);

    List<OrderEntryDetails> toOrderEntryDetails(List<OrderDto> orderDtoList);
}