package com.example.demo;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.exceptions.ValidationsException;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.DefaultUserService;

import com.example.demo.services.EmailAddressValidationService;
import com.example.demo.services.PasswordValidationService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
class UserServiceTests {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordValidationService passwordValidationService = mock(PasswordValidationService.class);
    private final EmailAddressValidationService emailValidationService = mock(EmailAddressValidationService.class);
    private final UserService userService = new DefaultUserService(userRepository, passwordValidationService, emailValidationService);

    @Test
    void registerUserSuccessfullyTest() {
        String email = "test@example.com";
        String password = "password123!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordValidationService.encodePassword(password)).thenReturn(BCrypt.hashpw(password, BCrypt.gensalt()));

        UserRegisterResponse response = userService.register(request);

        assertTrue(response.isSuccess());
        assertEquals(email, response.getEmail());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(email, savedUser.getEmail());
        assertNotEquals(password, savedUser.getPassword());
        assertTrue(BCrypt.checkpw(password, savedUser.getPassword()));
    }

    @Test
    void registerUserFailsWhenEmailExistsTest() {
        String email = "test@example.com";
        String password = "Password123!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        assertThrows(ResourceAlreadyExistsException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUserFailsWhenPasswordsDoNotMatchTest() {
        String email = "test@example.com";
        String password = "Password123!";
        String confirmedPassword = "Password123!4";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                confirmedPassword
        );
        doThrow(new ValidationException("Passwords do not match."))
                .when(passwordValidationService)
                .comparePasswords(password, confirmedPassword);

        assertThrows(ValidationException.class, () -> userService.register(request));

        verify(passwordValidationService, times(1)).comparePasswords(password, confirmedPassword);
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUserFailsWhenPasswordIsInValidTest() {
        String email = "test@example.com";
        String password = "Password";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );
        doThrow(new ValidationsException(new ArrayList<>()))
                .when(passwordValidationService)
                .validatePassword(password);

        assertThrows(ValidationsException.class, () -> userService.register(request));

        verify(passwordValidationService, times(1)).validatePassword(password);
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUserFailsWhenEmailAddressIsInValidTest() {
        String email = "testexample.com";
        String password = "Password";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );
        doThrow(new ValidationsException(new ArrayList<>()))
                .when(emailValidationService)
                .validateEmail(email);

        assertThrows(ValidationsException.class, () -> userService.register(request));

        verify(emailValidationService, times(1)).validateEmail(email);
        verify(userRepository, never()).save(any());
    }

}
