package com.example.demo.validators;

import java.util.ArrayList;
import java.util.List;

public abstract class PasswordValidator {
    protected PasswordValidator validator;

    public static PasswordValidator link(PasswordValidator first, PasswordValidator... chain) {
        PasswordValidator head = first;
        for (PasswordValidator nextInChain : chain) {
            head.validator = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public List<String> validate(String password) {
        List<String> errors = new ArrayList<>();
        check(password, errors);
        if (validator != null) {
            errors.addAll(validator.validate(password));
        }
        return errors;
    }

    protected abstract void check(String password, List<String> errors);
}