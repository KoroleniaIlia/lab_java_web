package com.example.lab_java_web.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpaceCategoryValidation.class)
public @interface ValidSpaceCategory {
    String message() default "Invalid Space Categories it must be: CosmoMilk, Threads, CosmoCar, CosmoToys, Games, or Other if you didn't find right categories";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}