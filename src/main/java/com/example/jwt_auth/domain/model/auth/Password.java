package com.example.jwt_auth.domain.model.auth;

import org.springframework.util.StringUtils;

public record Password(String value) {
    public Password {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Passwordは空ではいけません");
        }
        if (value.length() < 8 || 20 < value.length()) {
            throw new IllegalArgumentException("Passwordは8文字以上20文字以下である必要があります");
        }
        // TODO: パスワードポリシーの追加
    }
}
