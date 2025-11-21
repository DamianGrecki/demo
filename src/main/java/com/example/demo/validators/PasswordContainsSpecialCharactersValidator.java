package com.example.demo.validators;

import java.util.List;
import java.util.regex.Pattern;

public class PasswordContainsSpecialCharactersValidator extends PasswordValidator {
    private static final Pattern SPECIAL_CHARACTER_PATTERN =
            Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

    @Override
    protected void check(String password, List<String> errors) {
        if (!SPECIAL_CHARACTER_PATTERN.matcher(password).matches()) {
            errors.add("Password should contain an special character.");
        }
    }
}
