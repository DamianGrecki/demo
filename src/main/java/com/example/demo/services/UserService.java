package com.example.demo.services;

import com.example.demo.models.requests.UserRegisterRequestBody;
import com.example.demo.models.responses.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequestBody request);
}
