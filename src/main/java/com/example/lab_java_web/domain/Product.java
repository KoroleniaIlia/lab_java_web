package com.example.lab_java_web.domain;


import com.example.lab_java_web.common.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;
    private Double price;
    private String name;
    private CategoryType categories;
    private String description;
}