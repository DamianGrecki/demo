package com.example.demo.services;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequestBody request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmedPassword = request.getConfirmPassword();

        comparePasswords(password, confirmedPassword);
        addUser(email, password);
        return new UserRegisterResponse(true, email);
    }

    private void addUser(String email, String password) {
        validateEmailUniqueness(email);
        User user = new User(
                email,
                encodePassword(password)
        );
        userRepository.save(user);
    }

    private void comparePasswords(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords do not match.");
        }
    }

    private String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    String.format("User with email '%s' already exists.", email)
            );
        }
    }

}