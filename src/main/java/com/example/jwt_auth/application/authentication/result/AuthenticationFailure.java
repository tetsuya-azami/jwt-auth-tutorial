package com.example.jwt_auth.application.authentication.result;

public record AuthenticationFailure(String errorMessage) implements AuthenticationResult {
}
