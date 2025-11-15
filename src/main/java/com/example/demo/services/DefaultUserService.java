package com.example.demo.services;

import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import com.example.demo.models.UserRegisterDataDto;
import com.example.demo.models.UserRegisterResponse;
import com.example.demo.utility.PasswordValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterResponse register(UserRegisterDataDto request) {
        String password = request.getPassword();
        String email = request.getEmail();
        PasswordValidator.validate(password);
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(email, encodedPassword);
        userRepository.save(user);

        return new UserRegisterResponse(true);
    }

}