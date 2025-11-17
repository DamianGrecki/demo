package com.example.demo;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.DefaultUserService;

import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new DefaultUserService(userRepository);
    }

    @Test
    void registerUserSuccessfullyTest() {
        String email = "test@example.com";
        String password = "password123";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserRegisterResponse response = userService.register(request);

        assertTrue(response.isSuccess());
        assertEquals(email, response.getEmail());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(email, savedUser.getEmail());
        assertNotEquals(password, savedUser.getPassword());
        assertTrue(BCrypt.checkpw(password, savedUser.getPassword()));
    }

    @Test
    void registerUserFailsWhenEmailExistsTest() {
        String email = "test@example.com";
        String password = "Password123";

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
        String password = "Password123";
        String confirmedPassword = "Password1234";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                confirmedPassword
        );

        assertThrows(ValidationException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any());
    }

}
