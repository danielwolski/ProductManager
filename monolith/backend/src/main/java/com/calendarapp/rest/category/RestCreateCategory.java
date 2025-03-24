package com.calendarapp.rest.category;

import jakarta.validation.constraints.NotBlank;

public record RestCreateCategory(
        @NotBlank String name
) {}
