package com.example.lab_java_web.dto.validation;

import com.example.lab_java_web.common.Categories;
import com.example.lab_java_web.service.mapper.ProductServiceMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class SpaceCategoryValidation implements ConstraintValidator<ValidSpaceCategory, String> {
    private final ProductServiceMapper productServiceMapper;

    @Override
    public boolean isValid(String categories, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(Categories.values())
                .anyMatch(type -> productServiceMapper.toCategoryString(type).equalsIgnoreCase(categories));
    }
}