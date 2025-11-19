package com.example.demo.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.example.demo.constants.ValidationMessages.*;

public class UserRegisterRequestBody {

    @NotBlank(message = EMAIL_IS_REQUIRED_MSG)
    @Email(message = EMAIL_MUST_BE_VALID_MSG)
    private final String email;

    @NotBlank(message = PASSWORD_IS_REQUIRED_MSG)
    @Size(min = 8, max = 128, message = PASSWORD_MIN_MAX_LENGTH_MSG)
    @Pattern(regexp = ".*[A-Z].*", message = PASSWORD_REQUIRE_UPPERCASE_LETTER_MSG)
    @Pattern(regexp = ".*[a-z].*", message = PASSWORD_REQUIRE_LOWERCASE_LETTER_MSG)
    @Pattern(regexp = ".*\\d.*", message = PASSWORD_REQUIRE_DIGIT_MSG)
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*", message = PASSWORD_REQUIRE_SPECIAL_CHARACTER_MSG)
    private final String password;

    @NotBlank(message = PASSWORD_CONFIRMATION_REQUIRED_MSG)
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
