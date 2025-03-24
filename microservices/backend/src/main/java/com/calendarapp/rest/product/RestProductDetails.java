package com.calendarapp.rest.product;

import java.math.BigDecimal;

public record RestProductDetails(
        String id,
        String name,
        String description,
        BigDecimal price,
        String categoryName
) {}
