package com.example.jwt_auth.application.authentication;

public record AuthenticationFailure(String errorMessage) implements AuthenticationResult {
}
