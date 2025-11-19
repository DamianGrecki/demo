package com.example.demo;

import com.example.demo.models.requests.UserRegisterRequestBody;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static com.example.demo.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRegisterRequestTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void passwordValidationSuccessfulTest() {
        String email = "test@example.com";
        String password = "Password123!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        assertTrue(validator.validate(request).isEmpty());
    }


    @ParameterizedTest
    @MethodSource("passwordProvider")
    void passwordValidationNegativeTest(String password, String message) {
        String email = "test@example.com";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );
        Set<ConstraintViolation<UserRegisterRequestBody>> results = validator.validate(request);
        assertEquals(1, results.size(), String.format("Expected 1 validation error but got: %d", results.size()));
        boolean isError = results.stream()
                .anyMatch(v -> v.getMessageTemplate().equals(message));

        assertTrue(isError);
    }

    @ParameterizedTest
    @MethodSource("emailProvider")
    void emailValidationNegativeTest(String email, String message) {
        String password = "Password1!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );
        Set<ConstraintViolation<UserRegisterRequestBody>> results = validator.validate(request);
        assertEquals(1, results.size(), String.format("Expected 1 validation error but got: %d", results.size()));
        boolean isError = results.stream()
                .anyMatch(v -> v.getMessageTemplate().equals(message));

        assertTrue(isError);
    }

    private static Stream<Arguments> passwordProvider() {
        String longPassword = "Password1!" + "a".repeat(130);
        return Stream.of(
                Arguments.of(longPassword, PASSWORD_MIN_MAX_LENGTH_MSG),
                Arguments.of("Pw1!", PASSWORD_MIN_MAX_LENGTH_MSG),
                Arguments.of("password1!", PASSWORD_REQUIRE_UPPERCASE_LETTER_MSG),
                Arguments.of("PASSWORD1!", PASSWORD_REQUIRE_LOWERCASE_LETTER_MSG),
                Arguments.of("Password!", PASSWORD_REQUIRE_DIGIT_MSG),
                Arguments.of("Password1", PASSWORD_REQUIRE_SPECIAL_CHARACTER_MSG)
        );
    }

    private static Stream<Arguments> emailProvider() {
        return Stream.of(
                Arguments.of("testexample.pl", EMAIL_MUST_BE_VALID_MSG),
                Arguments.of("test.example.pl", EMAIL_MUST_BE_VALID_MSG),
                Arguments.of("test@example,pl", EMAIL_MUST_BE_VALID_MSG),
                Arguments.of("test@example.pl.", EMAIL_MUST_BE_VALID_MSG),
                Arguments.of("test@example..pl", EMAIL_MUST_BE_VALID_MSG)
        );
    }

}
