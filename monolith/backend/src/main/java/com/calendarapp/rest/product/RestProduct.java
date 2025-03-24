package com.calendarapp.rest.product;

import java.math.BigDecimal;

public record RestProduct(
        String id,
        String name,
        BigDecimal price,
        String categoryName
) {}
