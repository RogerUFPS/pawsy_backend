package com.web.project.dto;

public class VerificationResponse {
    private String token;

    public VerificationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
