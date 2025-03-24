package com.calendarapp.mapper;

import com.calendarapp.model.Category;
import com.calendarapp.rest.category.RestCategory;
import com.calendarapp.rest.category.RestCreateCategory;

import java.util.List;

public class CategoryMapper {

    public static Category restCreateCategoryToCategory(RestCreateCategory restCreateCategory) {
        return Category.builder()
                .name(restCreateCategory.name())
                .build();
    }

    public static RestCategory categoryToRestCategory(Category category) {
        return new RestCategory(
                category.getId(),
                category.getName()
        );
    }

    public static List<RestCategory> categoryListToRestCategoryList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::categoryToRestCategory)
                .toList();
    }
}
