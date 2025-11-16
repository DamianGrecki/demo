package com.example.demo.models.responses;

public class UserRegisterResponse {

    private final boolean isSuccess;
    private final String email;

    public UserRegisterResponse(boolean isSuccess, String email) {
        this.isSuccess = isSuccess;
        this.email = email;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
    public String getEmail() {
        return email;
    }
}
