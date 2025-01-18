package com.example.jwt_auth.domain.service.auth;

import com.example.jwt_auth.domain.model.auth.Password;
import com.example.jwt_auth.domain.model.auth.User;
import com.example.jwt_auth.domain.model.auth.UserId;
import com.example.jwt_auth.domain.model.auth.UserName;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * ユーザー認証を行うServiceクラス
 */
@Service
public class UserAuthenticator {
    private final Map<UserId, User> users = Map.of(
            new UserId("11111"), new User(new UserId("11111"), new UserName("user1"), new Password("password1")),
            new UserId("22222"), new User(new UserId("22222"), new UserName("user2"), new Password("password2")),
            new UserId("33333"), new User(new UserId("33333"), new UserName("user3"), new Password("password3")));

    public Optional<User> authenticateAndGetUser(UserId userId, Password password) {
        User user = users.get(userId);
        if (user == null || !password.equals(user.password())) return Optional.empty();

        return Optional.of(user);
    }
}
