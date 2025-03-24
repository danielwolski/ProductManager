package com.calendarapp.validator;

import com.calendarapp.exception.CategoryAlreadyExistsException;
import com.calendarapp.exception.CategoryNotFoundException;
import com.calendarapp.exception.ProductsWithCategoryExistsException;
import com.calendarapp.model.Category;
import com.calendarapp.repository.CategoryRepository;
import com.calendarapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public void validateCreate(Category categoryToCreate) {
        validateCategoryName(categoryToCreate.getName());
    }

    public void validateDelete(String id) {
        validateNoProductsForCategoryExist(id);
    }

    private void validateCategoryName(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            throw new CategoryAlreadyExistsException(categoryName);
        }
    }

    private void validateNoProductsForCategoryExist(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        if (!productRepository.findAllByCategoryName(category.getName()).isEmpty()) {
            throw new ProductsWithCategoryExistsException(category.getName());
        }
    }
}
