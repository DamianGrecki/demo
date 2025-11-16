package com.example.demo.services;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequestBody request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmedPassword = request.getConfirmPassword();

        comparePasswords(password, confirmedPassword);
        saveUser(email, password);
        return new UserRegisterResponse(true, email);
    }

    private void comparePasswords(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords do not match.");
        }
    }

    private void saveUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    String.format("User with email '%s' already exist.", email)
            );
        }
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

}