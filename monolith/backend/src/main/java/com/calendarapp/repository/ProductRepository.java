package com.calendarapp.repository;

import com.calendarapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByCategoryName(String category);
}
