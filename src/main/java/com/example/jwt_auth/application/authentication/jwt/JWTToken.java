package com.example.jwt_auth.application.authentication.jwt;

// TODO: テスト作成
public record JWTToken(String value) {
    public JWTToken {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("トークンは空ではいけません");
        }
    }
}