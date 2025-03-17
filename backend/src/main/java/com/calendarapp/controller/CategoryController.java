package com.calendarapp.controller;

import com.calendarapp.mapper.CategoryMapper;
import com.calendarapp.mapper.ProductMapper;
import com.calendarapp.model.Category;
import com.calendarapp.rest.category.RestCategory;
import com.calendarapp.rest.category.RestCreateCategory;
import com.calendarapp.rest.product.RestProduct;
import com.calendarapp.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<RestCategory>> getAllCategories() {
        List<RestCategory> restCategoryList =
                CategoryMapper.categoryListToRestCategoryList(categoryService.getAllCategories());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(restCategoryList);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid  @RequestBody RestCreateCategory restCreateCategory) {
        log.info("Received create category request {}", restCreateCategory.name());
        Category createdCategory =
                categoryService.createCategory(CategoryMapper.restCreateCategoryToCategory(restCreateCategory));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable String id) {
        log.info("Received delete category request {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
