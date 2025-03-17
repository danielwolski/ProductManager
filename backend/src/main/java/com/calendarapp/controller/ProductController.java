package com.calendarapp.controller;

import com.calendarapp.mapper.ProductMapper;
import com.calendarapp.model.Product;
import com.calendarapp.rest.product.RestCreateProduct;
import com.calendarapp.rest.product.RestProduct;
import com.calendarapp.rest.product.RestProductDetails;
import com.calendarapp.rest.product.RestUpdateProduct;
import com.calendarapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody RestCreateProduct restCreateProduct) {
        log.info("Received create product request {}", restCreateProduct.name());
        Product createdProduct =
                productService.createProduct(ProductMapper.restCreateProductToProduct(restCreateProduct));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestProductDetails> getProductDetails(@PathVariable String id) {
        RestProductDetails restProductDetails =
                ProductMapper.productToRestProductDetails(productService.getProductDetails(id));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(restProductDetails);
    }

    @GetMapping
    public ResponseEntity<List<RestProduct>> getAllProducts() {
        List<RestProduct> restProductList =
                ProductMapper.productListToRestProductList(productService.getAllProducts());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(restProductList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestProduct> updateProduct(@PathVariable String id, @Valid @RequestBody RestUpdateProduct restUpdateProduct) {
        log.info("Received update product request {}", restUpdateProduct.name());
        RestProduct updatedProduct =
                ProductMapper.productToRestProduct(
                        productService.updateProduct(ProductMapper.restUpdateProductToProduct(restUpdateProduct)));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable String id) {
        log.info("Received delete product request {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RestProduct>> getAllProductsByCategory(@PathVariable String category) {
        List<RestProduct> restProductList =
                ProductMapper.productListToRestProductList(
                        productService.getAllProductsByCategory(category));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(restProductList);
    }
}
