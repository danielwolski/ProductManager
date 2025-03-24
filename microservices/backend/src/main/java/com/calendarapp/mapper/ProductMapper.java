package com.calendarapp.mapper;

import com.calendarapp.model.Product;
import com.calendarapp.rest.product.RestCreateProduct;
import com.calendarapp.rest.product.RestProduct;
import com.calendarapp.rest.product.RestProductDetails;
import com.calendarapp.rest.product.RestUpdateProduct;

import java.util.List;

public class ProductMapper {

    public static RestProductDetails productToRestProductDetails(Product product) {
        return new RestProductDetails(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategoryName()
        );
    }

    public static RestProduct productToRestProduct(Product product) {
        return new RestProduct(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategoryName());
    }

    public static Product restCreateProductToProduct(RestCreateProduct restCreateProduct) {
        return Product.builder()
                .name(restCreateProduct.name())
                .description(restCreateProduct.description())
                .price(restCreateProduct.price())
                .categoryName(restCreateProduct.categoryName())
                .build();
    }

    public static Product restUpdateProductToProduct(RestUpdateProduct restUpdateProduct) {
        return Product.builder()
                .id(restUpdateProduct.id())
                .name(restUpdateProduct.name())
                .description(restUpdateProduct.description())
                .price(restUpdateProduct.price())
                .categoryName(restUpdateProduct.categoryName())
                .build();
    }

    public static List<RestProduct> productListToRestProductList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::productToRestProduct)
                .toList();
    }
}
