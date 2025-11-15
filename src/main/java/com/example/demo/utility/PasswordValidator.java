package com.example.demo.utility;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    private PasswordValidator(){}

    public static void validate(String password) {
        validateLength(password);
        validateUppercase(password);
        validateDigit(password);
    }

    private static void validateLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Password length should be more than %s chars", MIN_PASSWORD_LENGTH)
            );
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Password length should be less than %s chars", MAX_PASSWORD_LENGTH)
            );
        }
    }

    private static void validateUppercase(String password) {
        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Password should contain an uppercase letter");
        }
    }

    private static void validateDigit(String password) {
        if (!DIGIT_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Password should contain a digit");
        }
    }
}
