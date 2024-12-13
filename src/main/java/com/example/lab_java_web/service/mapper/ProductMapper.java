package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.common.CategoryType;
import com.example.lab_java_web.domain.Product;
import com.example.lab_java_web.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoryString")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    ProductDTO toProductDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoryType")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    Product toProduct(ProductDTO productDto);

    List<ProductDTO> toProductDtoList(List<Product> products);
    @Named("toCategoryString")
    default String toCategoryString(CategoryType categoryType) {
        return categoryType.getDisplayName();
    }

    @Named("toCategoryType")
    default CategoryType toCategoryType(String categoryString) {
        return CategoryType.valueOf(categoryString.toUpperCase());
    }

}