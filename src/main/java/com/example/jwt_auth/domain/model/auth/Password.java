package com.example.jwt_auth.domain.model.auth;

import org.springframework.util.StringUtils;

public record Password(String value) {
    public Password {
        if (StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Passwordは空ではいけません");
        }
        if (value.length() < 8) {
            throw new IllegalArgumentException("Passwordは8文字以上である必要があります");
        }
        // TODO: パスワードポリシーの追加
    }
}
