package com.web.project.dto;

public class RegisterResponse {
    private String token;

    public RegisterResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}