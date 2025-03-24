package com.calendarapp.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Category with name=" + name + " not found");
    }
}