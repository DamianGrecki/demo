package com.example.demo.validators;

import java.util.List;
import java.util.regex.Pattern;

public class PasswordContainsLowerCaseValidator extends PasswordValidator {
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[a-z].*");

    @Override
    protected void check(String password, List<String> errors) {
        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            errors.add("Password should contain an lowercase letter.");
        }
    }
}
