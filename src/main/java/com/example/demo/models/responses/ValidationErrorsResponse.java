package com.example.demo.models.responses;

import java.util.List;
import java.util.Map;

public class ValidationErrorsResponse {
    private final List<String> errors;
    private final boolean isSuccess = false;

    public ValidationErrorsResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
