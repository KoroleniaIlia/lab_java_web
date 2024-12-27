package com.example.lab_java_web.repository.mapper;

import com.example.lab_java_web.common.Categories;
import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.repository.entity.ProductEntity;

import java.util.ArrayList;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductRepositoryMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "categories", source = "categories")
    @Named("toProductDetails")
    ProductDetails toProductDetails(ProductEntity productEntity);

    List<ProductDetails> toProductDetails(List<ProductEntity> productEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "categories", source = "categories")
    @Named("toProductEntity")
    ProductEntity toProductEntity(ProductDetails productDetails);

    List<ProductEntity> toProductEntity(List<ProductDetails> productDetails);

    default List<Categories> categoryToList(Categories categoryType) {
        List<Categories> categories = new ArrayList<>();
        if (categoryType != null) {
            categories.add(categoryType);
        }
        return categories;
    }

    default Categories listToCategoryType(List<Categories> categories) {
        return categories != null && !categories.isEmpty() ? categories.get(0) : null;
    }
}