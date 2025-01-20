package com.example.jwt_auth.presentation.calculation;

public sealed interface CalculationResponse {
    record Success(String code, String message) implements CalculationResponse {
    }

    record Failure(String errorCode, String errorMessage) implements CalculationResponse {
    }
}
