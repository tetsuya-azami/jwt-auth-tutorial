package com.example.jwt_auth.application.authentication;

import com.example.jwt_auth.domain.model.auth.JWTToken;

public sealed interface AuthenticationResult permits AuthenticationSuccess, AuthenticationFailure {
}

record AuthenticationSuccess(JWTToken token) implements AuthenticationResult {
}

record AuthenticationFailure(String errorMessage) implements AuthenticationResult {
}