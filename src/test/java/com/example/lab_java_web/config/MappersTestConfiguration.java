package com.example.lab_java_web.config;


import com.example.lab_java_web.service.mapper.CosmoCatMapper;
import com.example.lab_java_web.service.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
@TestConfiguration
public class MappersTestConfiguration {
    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public CosmoCatMapper cosmicCatMapper() {
        return Mappers.getMapper(CosmoCatMapper.class);
    }
}