package com.example.demo.services;

import com.example.demo.models.UserRegisterDataDto;
import com.example.demo.models.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse register(UserRegisterDataDto request);
}
