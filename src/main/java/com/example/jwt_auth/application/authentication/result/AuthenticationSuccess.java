package com.example.jwt_auth.application.authentication.result;

import com.example.jwt_auth.application.authentication.JWTToken;

public record AuthenticationSuccess(JWTToken token) implements AuthenticationResult {
}