package com.example.lab_java_web.dto;

import com.example.lab_java_web.dto.validation.ValidSpaceCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ProductDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can't be more than 100 symbols")
    private String name;
    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description can't be more than 255 symbols")
    private String description;
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price can't be negative or zero")
    private Double price;
    @NotEmpty(message = "Category can't be empty")
    @ValidSpaceCategory
    private String categories;


}