package com.example.demo.services;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.constants.Messages.EMAIL_ADDRESS_ALREADY_EXISTS_MSG;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordValidationService passwordValidationService;
    private final EmailAddressValidationService emailValidationService;

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequestBody request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmedPassword = request.getConfirmPassword();
        emailValidationService.validateEmail(email);
        passwordValidationService.validatePassword(password);
        passwordValidationService.comparePasswords(password, confirmedPassword);
        addUser(email, password);
        return new UserRegisterResponse(true, email);
    }

    private void addUser(String email, String password) {
        validateEmailUniqueness(email);
        User user = new User(
                email,
                passwordValidationService.encodePassword(password)
        );
        userRepository.save(user);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    String.format(EMAIL_ADDRESS_ALREADY_EXISTS_MSG, email)
            );
        }
    }

}