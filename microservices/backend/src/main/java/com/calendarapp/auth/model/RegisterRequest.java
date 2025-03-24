package com.calendarapp.auth.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String password;
}
