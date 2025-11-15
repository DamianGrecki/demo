package com.example.demo.models;

public class UserRegisterResponse {

    private final boolean validatorPassed;

    public UserRegisterResponse(boolean validatorPassed) {
        this.validatorPassed = validatorPassed;
    }


    public boolean isValidatorPassed() {
        return validatorPassed;
    }
}
