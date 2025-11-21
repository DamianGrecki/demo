package com.example.demo.validators;

import java.util.List;
import java.util.regex.Pattern;

public class PasswordContainsDigitValidator extends PasswordValidator {
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    @Override
    protected void check(String password, List<String> errors) {
        if (!DIGIT_PATTERN.matcher(password).matches()) {
            errors.add("Password should contain a digit");
        }
    }
}