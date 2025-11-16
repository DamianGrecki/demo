package com.example.demo.models.responses;

public class ErrorResponse {

    private final String message;
    private final boolean isSuccess = false;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
