package com.example.jwt_auth.application.authentication.jwt.result;

public record AuthenticationFailure(String errorMessage) implements AuthenticationResult {
}
