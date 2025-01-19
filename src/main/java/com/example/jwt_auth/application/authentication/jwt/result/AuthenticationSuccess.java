package com.example.jwt_auth.application.authentication.jwt.result;

import com.example.jwt_auth.application.authentication.jwt.JWTToken;

public record AuthenticationSuccess(JWTToken token) implements AuthenticationResult {
}