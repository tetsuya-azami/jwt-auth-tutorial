package com.example.jwt_auth.domain.model.auth;

import org.springframework.util.StringUtils;

public class UserName {
    public UserName(String value) {
        if (StringUtils.hasLength(value)) {
            throw new IllegalArgumentException("Usernameは空ではいけません");
        }
        if (value.length() < 3 || 20 < value.length()) {
            throw new IllegalArgumentException("Usernameは3文字以上20文字以下である必要があります");
        }
    }
}
