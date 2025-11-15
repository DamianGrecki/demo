package com.example.demo.controllers;

import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import com.example.demo.models.UserRegisterDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<?> register(@Valid @RequestBody UserRegisterDataDto request) {
        return ResponseEntity.ok().body(userService.register(request));
    }

}
