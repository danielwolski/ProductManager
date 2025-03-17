package com.calendarapp.service;

import com.calendarapp.exception.ProductNotFoundException;
import com.calendarapp.model.Product;
import com.calendarapp.repository.ProductRepository;
import com.calendarapp.validator.ProductValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;


    public Product createProduct(Product productToCreate) {
        productValidator.validateCreate(productToCreate);
        return productRepository.save(productToCreate);
    }

    public Product getProductDetails(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product productUpdate) {
        productValidator.validateUpdate(productUpdate);

        Product existingProduct = productRepository.findById(productUpdate.getId())
                .orElseThrow(() -> new ProductNotFoundException(productUpdate.getId()));

        existingProduct.setName(productUpdate.getName());
        existingProduct.setDescription(productUpdate.getDescription());
        existingProduct.setPrice(productUpdate.getPrice());
        existingProduct.setCategoryName(productUpdate.getCategoryName());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findAllByCategoryName(category);
    }
}
