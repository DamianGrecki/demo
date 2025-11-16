package com.example.demo.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterRequestBody {

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private final String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 128, message = "Password must be between {min} and {max} characters long.")
    @Pattern(regexp = ".*[A-Z].*", message = "Password should contain a uppercase letter.")
    @Pattern(regexp = ".*[a-z].*", message = "Password should contain a lowercase letter.")
    @Pattern(regexp = ".*\\d.*", message = "Password should contain a digit.")
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*", message = "Password should contain special character.")
    private final String password;

    @NotBlank(message = "Password confirmation is required.")
    private final String confirmPassword;

    public UserRegisterRequestBody(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
