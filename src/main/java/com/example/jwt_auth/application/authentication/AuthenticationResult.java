package com.example.jwt_auth.application.authentication;

public sealed interface AuthenticationResult permits AuthenticationSuccess, AuthenticationFailure {
}


