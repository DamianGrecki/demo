package com.example.demo.constants;

public class ValidationMessages {
    private ValidationMessages() {
    }

    public static final String EMAIL_IS_REQUIRED_MSG = "Email is required.";
    public static final String EMAIL_MUST_BE_VALID_MSG = "Email must be valid.";

    public static final String PASSWORD_IS_REQUIRED_MSG = "Password is required.";
    public static final String PASSWORD_CONFIRMATION_REQUIRED_MSG = "Password confirmation is required.";
    public static final String PASSWORD_MIN_MAX_LENGTH_MSG = "Password must be between {min} and {max} characters long.";
    public static final String PASSWORD_REQUIRE_UPPERCASE_LETTER_MSG = "Password should contain a uppercase letter.";
    public static final String PASSWORD_REQUIRE_LOWERCASE_LETTER_MSG = "Password should contain a lowercase letter.";
    public static final String PASSWORD_REQUIRE_DIGIT_MSG = "Password should contain a digit.";
    public static final String PASSWORD_REQUIRE_SPECIAL_CHARACTER_MSG = "Password should contain special character.";
}
