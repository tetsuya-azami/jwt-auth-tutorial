package com.example.jwt_auth.presentation.authentication;

public sealed interface AuthenticationResponse {
    record Success(String token) implements AuthenticationResponse {
    }

    record Failure(String errorMessage) implements AuthenticationResponse {
    }
}
