package com.example.demo.integration;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUserServiceIntegrationTests extends BaseIntegrationTest {

    @Autowired
    DefaultUserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldRegisterUserTest() {
        String email = "test@example.com";
        String password = "Password123!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        UserRegisterResponse response = userService.register(request);
        assertTrue(response.isSuccess());
        assertEquals(email, response.getEmail());

        assertEquals(1, userRepository.count());
        User savedUser = userRepository.findByEmail(email)
                .orElseThrow();
        assertEquals(email, savedUser.getEmail());
        assertTrue(savedUser.getPassword().startsWith("$2"));
    }

    @Test
    void shouldThrowExceptionWhenEmailExistsTest() {
        String email = "test@example.com";
        String password = "Password123!";

        UserRegisterRequestBody request = new UserRegisterRequestBody(
                email,
                password,
                password
        );

        userService.register(request);
        assertEquals(1, userRepository.count());

        assertThrows(ResourceAlreadyExistsException.class,
                () -> userService.register(request)
        );
        assertEquals(1, userRepository.count());
    }
}
