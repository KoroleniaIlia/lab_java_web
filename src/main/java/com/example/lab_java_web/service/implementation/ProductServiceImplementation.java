package com.example.lab_java_web.service.implementation;


import com.example.lab_java_web.domain.ProductDetails;
import com.example.lab_java_web.repository.ProductRepository;
import com.example.lab_java_web.repository.entity.ProductEntity;
import com.example.lab_java_web.repository.mapper.ProductRepositoryMapper;
import com.example.lab_java_web.service.ProductService;
import com.example.lab_java_web.service.exeption.ProductNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepositoryMapper productRepositoryMapper;
    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepositoryMapper productRepositoryMapper, ProductRepository productRepository) {
        this.productRepositoryMapper = productRepositoryMapper;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDetails> getAllProducts() {
        return productRepositoryMapper.toProductDetails(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductDetails getProductByProductId(UUID productId) {
        return productRepositoryMapper.toProductDetails(productRepository.findByNaturalId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId)));
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public ProductDetails saveProduct(ProductDetails productId) {
        return productRepositoryMapper.toProductDetails(
                productRepository.save(productRepositoryMapper.toProductEntity(productId)));
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public ProductDetails saveProduct(UUID productId, ProductDetails productDetails) {
        ProductEntity oldProduct = productRepository.findByNaturalId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        oldProduct.setName(productDetails.getName());
        oldProduct.setDescription(productDetails.getDescription());
        oldProduct.setPrice(productDetails.getPrice());
        oldProduct.setCategories(productRepositoryMapper.categoryToList(productDetails.getCategories()));
        productRepository.save(oldProduct);
        return productRepositoryMapper.toProductDetails(oldProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.findByNaturalId(productId);
        productRepository.deleteByNaturalId(productId);
    }

    @Override
    public void cleanProductList() {

    }

}
