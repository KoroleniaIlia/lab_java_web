package com.example.lab_java_web.domain;


import com.example.lab_java_web.common.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductDetails {
    Long id;
    UUID productId;
    Double price;
    String name;
    Categories categories;
    String description;
}
