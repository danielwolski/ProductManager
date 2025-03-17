package com.calendarapp.exception;

public class ProductsWithCategoryExistsException extends RuntimeException {
    public ProductsWithCategoryExistsException(String name) {
        super("Cannot delete category because product(s) with categoryName=" + name + " exist");
    }
}
