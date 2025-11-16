package com.example.demo.models.responses;

import java.util.List;
import java.util.Map;

public class ValidationErrorsResponse {
    private final Map<String, List<String>> errors;
    private final boolean isSuccess = false;

    public ValidationErrorsResponse(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
