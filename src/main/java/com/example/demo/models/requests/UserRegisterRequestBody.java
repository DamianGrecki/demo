package com.example.demo.models.requests;

public class UserRegisterRequestBody {

    private final String email;
    private final String password;
    private final String confirmPassword;

    public UserRegisterRequestBody(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
