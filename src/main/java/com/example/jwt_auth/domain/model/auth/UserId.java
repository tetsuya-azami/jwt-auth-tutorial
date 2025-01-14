package com.example.jwt_auth.domain.model.auth;

import org.springframework.util.StringUtils;

public record UserId(String value) {
    public UserId {
        if (!StringUtils.hasLength(value)){
            throw new IllegalArgumentException("ユーザIDは空ではいけません");
        }
        if (value.length() < 5 || 20 < value.length()){
            throw new IllegalArgumentException("ユーザIDは5文字以上20文字以下である必要があります");
        }
    }
}
