package com.example.lab_java_web.config;


import com.example.lab_java_web.service.mapper.CosmoCatServiceMapper;
import com.example.lab_java_web.service.mapper.OrderServiceMapper;
import com.example.lab_java_web.service.mapper.ProductServiceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfiguration {
    @Bean
    public ProductServiceMapper productMapper() {
        return Mappers.getMapper(ProductServiceMapper.class);
    }

    @Bean
    public CosmoCatServiceMapper cosmoCatMapper() {
        return Mappers.getMapper(CosmoCatServiceMapper.class);
    }

    @Bean
    public OrderServiceMapper orderServiceMapper() {
        return Mappers.getMapper(OrderServiceMapper.class);
    }
}