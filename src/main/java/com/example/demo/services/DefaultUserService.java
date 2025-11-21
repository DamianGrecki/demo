package com.example.demo.services;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public DefaultUserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequestBody request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmedPassword = request.getConfirmPassword();
        passwordService.validatePasswords(password);
        passwordService.comparePasswords(password, confirmedPassword);
        addUser(email, password);
        return new UserRegisterResponse(true, email);
    }

    private void addUser(String email, String password) {
        validateEmailUniqueness(email);
        User user = new User(
                email,
                passwordService.encodePassword(password)
        );
        userRepository.save(user);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    String.format("User with email '%s' already exists.", email)
            );
        }
    }

}