package com.example.demo.validators;

import java.util.List;

public class PasswordLengthValidator extends PasswordValidator {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;

    @Override
    protected void check(String password, List<String> errors) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add(String.format("Password length should be more than %d chars.", MIN_PASSWORD_LENGTH));
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            errors.add(String.format("Password length should be less than %d chars.", MAX_PASSWORD_LENGTH));
        }
    }
}
