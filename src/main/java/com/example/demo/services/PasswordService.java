package com.example.demo.services;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.exceptions.ValidationsException;
import com.example.demo.validators.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {

    public void validatePasswords(String password) {
        validatePasswordPresence(password);
        PasswordValidator validator = PasswordValidator.link(
                new PasswordLengthValidator(),
                new PasswordContainsUpperCaseValidator(),
                new PasswordContainsLowerCaseValidator(),
                new PasswordContainsDigitValidator(),
                new PasswordContainsSpecialCharactersValidator()
        );
        List<String> errors = validator.validate(password);
        if (!errors.isEmpty()) {
            throw new ValidationsException(errors);
        }
    }

    public void comparePasswords(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords do not match.");
        }
    }

    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void validatePasswordPresence(String password) {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required.");
        }
    }
}
