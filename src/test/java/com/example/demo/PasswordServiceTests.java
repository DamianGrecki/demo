package com.example.demo;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.exceptions.ValidationsException;
import com.example.demo.services.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTests {

    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        passwordService = new PasswordService();
    }

    @Test
    void validatePasswordsShouldThrowForInvalidPasswordTest() {
        assertThrows(ValidationsException.class, () ->
                passwordService.validatePasswords("password1!")
        );
    }

    @Test
    void validatePasswordsShouldThrowForEmptyPasswordTest() {
        assertThrows(ValidationException.class, () ->
                passwordService.validatePasswords("")
        );
    }

    @Test
    void validatePasswordsShouldNotThrowForValidPasswordTest() {
        assertDoesNotThrow(() ->
                passwordService.validatePasswords("Password1!")
        );
    }

    @Test
    void comparePasswordsShouldThrowForNotEqualsPasswordsTest() {
        String password = "Password123!";
        String confirmedPassword = "Password123!!";
        assertThrows(ValidationException.class, () ->
                passwordService.comparePasswords(password, confirmedPassword)
        );
    }

    @Test
    void comparePasswordsShouldNotThrowForEqualsPasswordsTest() {
        String password = "Password123!";
        assertDoesNotThrow(() ->
                passwordService.comparePasswords(password, password)
        );
    }

    @Test
    void encodePasswordTest() {
        String password = "Password123!";
        String encodedPassword = passwordService.encodePassword(password);
        assertTrue(BCrypt.checkpw(password, encodedPassword));
    }
}