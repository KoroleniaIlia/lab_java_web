package com.example.lab_java_web.service.implementation;


import com.example.lab_java_web.common.CategoryType;
import com.example.lab_java_web.domain.Product;
import com.example.lab_java_web.service.ProductService;
import com.example.lab_java_web.service.exeption.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
    private final List<Product> listOfProducts = createProductList();

    @Override
    public List<Product> getAllProducts() {
        return listOfProducts;
    }

    @Override
    public Product getProductById(Long id) {
        return listOfProducts.stream()
                .filter(product -> product.getId().equals(id)).
                findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product createProduct(Product product) {
        product.setId((long) listOfProducts.size() + 1);
        listOfProducts.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        Product oldProduct = listOfProducts.stream()
                .filter(updatedProduct -> updatedProduct.getId().equals(product.getId())).findFirst().orElseThrow(() -> new ProductNotFoundException(product.getId()));
        oldProduct.setCategories(product.getCategories());
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        return oldProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        listOfProducts.remove(getProductById(id));
    }

    @Override
    public void cleanProductList() {
        listOfProducts.clear();
    }

    private List<Product> createProductList() {
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(Product.builder()
                .id(1L)
                .name("Космічне молоко")
                .categories(CategoryType.COSMOMILK)
                .description("Молоко космічної корови")
                .build());
        listOfProducts.add(Product.builder()
                .id(2L)
                .name("Котячі космічні ниткі")
                .categories(CategoryType.THREADS)
                .description("Спеціальні ниткі, чудове заспокійлеве для космічних котів")
                .build());
        listOfProducts.add(Product.builder()
                .id(3L)
                .name("Космічна машина")
                .categories(CategoryType.COSMOCAR)
                .description("Чудова річ для подорожів у космосі")
                .build());
        listOfProducts.add(Product.builder()
                .id(4L)
                .name("Космічні іграшки")
                .categories(CategoryType.COSMOTOYS)
                .description("Чудові іграшкі для космічних котиків")
                .build());
        listOfProducts.add(Product.builder()
                .id(5L)
                .name("Космічні відеоігри")
                .categories(CategoryType.GAMES)
                .description("Цікаві віртуальні відеоігри для скорочення часу довгих космічних подорожей")
                .build());
        listOfProducts.add(Product.builder()
                .id(6L)
                .name("Космічне каміння")
                .categories(CategoryType.OTHER)
                .description("Літає у космосі")
                .build());
        return listOfProducts;
    }
}
