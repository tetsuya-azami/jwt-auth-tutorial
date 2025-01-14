package com.example.jwt_auth.domain.model.auth;

public record User(UserId id, UserName username, Password password) {
    public User {
        if (id == null) {
            throw new IllegalArgumentException("userIdはnullではいけません");
        }
        if (username == null) {
            throw new IllegalArgumentException("usernameはnullではいけません");
        }
        if (password == null) {
            throw new IllegalArgumentException("passwordはnullではいけません");
        }
    }
}
