package com.example.jwtrolebased.dto;

public class TestResponse {

    private String message;

    public TestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
