package com.example.jwt_auth.application.authentication;

import com.example.jwt_auth.domain.model.authentication.JWTToken;

public record AuthenticationSuccess(JWTToken token) implements AuthenticationResult {
}