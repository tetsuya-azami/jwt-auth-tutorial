package com.example.jwt_auth.application.authentication.result;

public sealed interface AuthenticationResult permits AuthenticationSuccess, AuthenticationFailure {
}


