package com.example.jwt_auth.domain.model.authentication;

import org.springframework.util.StringUtils;

public record UserName(String value) {
    public UserName {
        if (!StringUtils.hasLength(value)) {
            throw new IllegalArgumentException("userNameは空ではいけません");
        }
        if (value.length() < 3 || 20 < value.length()) {
            throw new IllegalArgumentException("userNameは3文字以上20文字以下である必要があります");
        }
    }
}
