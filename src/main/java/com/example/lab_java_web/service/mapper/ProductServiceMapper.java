package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.common.Categories;
import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.dto.product.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductServiceMapper {
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoryString")
    @Named("toProductDto")
    ProductDTO toProductDto(ProductDetails productDetails);

    List<ProductDTO> toProductDto(List<ProductDetails> productDetailsList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoryType")
    @Mapping(target = "description", source = "description")
    ProductDetails toProductDetails(ProductDTO productDto);

    List<ProductDTO> toProductDtoList(List<ProductDetails> productDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Named("toProductDetailsWithReference")
    ProductDetails toProductDetailsWithReference(ProductDTO productDto);

    @Named("toCategoryString")
    default String toCategoryString(Categories categories) {
        return categories.getDisplayName();
    }

    @Named("toCategoryType")
    default Categories toCategoryType(String categoryString) {
        return Categories.valueOf(categoryString.toUpperCase());
    }
}