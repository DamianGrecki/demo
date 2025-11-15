package com.example.demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterDataDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private final String email;
    @NotBlank(message = "Password is required")
    private final String password;

    public UserRegisterDataDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
