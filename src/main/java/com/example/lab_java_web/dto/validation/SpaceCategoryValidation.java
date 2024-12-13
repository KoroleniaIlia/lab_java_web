package com.example.lab_java_web.dto.validation;

import com.example.lab_java_web.common.CategoryType;
import com.example.lab_java_web.service.mapper.ProductMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Stream;
@RequiredArgsConstructor
@Component
public class SpaceCategoryValidation implements ConstraintValidator<ValidSpaceCategory, String> {
    private final ProductMapper productMapper;
    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(CategoryType.values())
                .anyMatch(type -> productMapper.toCategoryString(type).equalsIgnoreCase(category));
    }
}