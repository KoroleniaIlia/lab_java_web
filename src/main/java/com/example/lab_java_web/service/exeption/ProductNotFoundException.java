package com.example.lab_java_web.service.exeption;

public class ProductNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Customer with id %s not found";
    public ProductNotFoundException(Long id) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }
}