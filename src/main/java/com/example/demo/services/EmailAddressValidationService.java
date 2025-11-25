package com.example.demo.services;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.exceptions.ValidationsException;
import com.example.demo.validators.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.EmailAddressConstraints.MAX_EMAIL_ADDRESS_LENGTH;
import static com.example.demo.constants.EmailAddressConstraints.MIN_EMAIL_ADDRESS_LENGTH;
import static com.example.demo.constants.Messages.*;

@Service
public class EmailAddressValidationService {

    public void validateEmail(String email) {
        validateEmailPresence(email);
        Validator validator = Validator.link(
                new StringLengthValidator(
                        MIN_EMAIL_ADDRESS_LENGTH,
                        MAX_EMAIL_ADDRESS_LENGTH,
                        MIN_EMAIL_ADDRESS_LENGTH_MSG,
                        MAX_EMAIL_ADDRESS_LENGTH_MSG
                ),
                new EmailAddressFormatValidator()

        );
        List<String> errors = validator.validate(email);
        if (!errors.isEmpty()) {
            throw new ValidationsException(errors);
        }
    }

    private void validateEmailPresence(String email) {
        if (email == null || email.isEmpty()) {
            throw new ValidationException(EMAIL_ADDRESS_IS_REQUIRED_MSG);
        }
    }
}
