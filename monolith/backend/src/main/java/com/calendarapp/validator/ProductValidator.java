package com.calendarapp.validator;

import com.calendarapp.exception.CategoryNotFoundException;
import com.calendarapp.model.Product;
import com.calendarapp.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductValidator {

    private final CategoryRepository categoryRepository;

    public void validateCreate(Product productToCreate) {
        validateCategoryName(productToCreate.getCategoryName());
    }

    public void validateUpdate(Product productToUpdate) {
        validateCategoryName(productToUpdate.getCategoryName());
    }

    private void validateCategoryName(String categoryName) {
        categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }
}
