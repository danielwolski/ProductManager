package com.calendarapp.service;

import com.calendarapp.model.Category;
import com.calendarapp.model.Product;
import com.calendarapp.repository.CategoryRepository;
import com.calendarapp.validator.CategoryValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category categoryToCreate) {
        categoryValidator.validateCreate(categoryToCreate);
        return categoryRepository.save(categoryToCreate);
    }

    public void deleteCategory(String id) {
        categoryValidator.validateDelete(id);
        categoryRepository.deleteById(id);
    }
}
