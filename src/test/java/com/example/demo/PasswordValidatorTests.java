package com.example.demo;

import com.example.demo.validators.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTests {

    @ParameterizedTest
    @MethodSource("inValidPasswordProvider")
    void validatePasswordsShouldThrowForInvalidPasswordTest(String password, PasswordValidator validator) {
        List<String> errors = validator.validate(password);
        assertEquals(1, errors.size());
    }

    private static Stream<Arguments> inValidPasswordProvider() {
        PasswordValidator passwordLengthValidator = new PasswordLengthValidator();
        String longPassword = "Password1!" + "a".repeat(130);
        return Stream.of(
                Arguments.of(longPassword, passwordLengthValidator),
                Arguments.of("Pw1!", passwordLengthValidator),
                Arguments.of("password1!", new PasswordContainsUpperCaseValidator()),
                Arguments.of("PASSWORD1!", new PasswordContainsLowerCaseValidator()),
                Arguments.of("Password!", new PasswordContainsDigitValidator()),
                Arguments.of("Password1", new PasswordContainsSpecialCharactersValidator())
        );
    }

    @ParameterizedTest
    @MethodSource("validatorProvider")
    void validatePasswordsShouldNotThrowForValidPasswordTest(PasswordValidator validator) {
        String password = "Password123!";
        List<String> errors = validator.validate(password);
        assertEquals(0, errors.size());
    }

    private static Stream<Arguments> validatorProvider() {
        return Stream.of(
                Arguments.of(new PasswordLengthValidator()),
                Arguments.of(new PasswordContainsUpperCaseValidator()),
                Arguments.of(new PasswordContainsLowerCaseValidator()),
                Arguments.of(new PasswordContainsDigitValidator()),
                Arguments.of(new PasswordContainsSpecialCharactersValidator())
        );
    }
}
