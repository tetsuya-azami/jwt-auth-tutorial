package com.example.jwt_auth.domain.service.auth;

import com.example.jwt_auth.domain.model.authentication.Password;
import com.example.jwt_auth.domain.model.authentication.User;
import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.model.authentication.UserName;
import com.example.jwt_auth.domain.service.authentication.UserAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserAuthenticatorTest {
    private UserAuthenticator userAuthenticator;

    @BeforeEach
    void setUp() {
        userAuthenticator = new UserAuthenticator();
    }

    @Test
    void userAuthentication_正常系() {
        User expectedUser = new User(new UserId("11111"), new UserName("user1"), new Password("password1"));
        User actualUser = userAuthenticator.authenticateAndGetUser(new UserId("11111"), new Password("password1")).orElse(null);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void userAuthentication_パスワードが一致しない() {
        assertNull(userAuthenticator.authenticateAndGetUser(new UserId("11111"), new Password("password2")).orElse(null));
    }

    @Test
    void userAuthentication_ユーザーIDが存在しない() {
        assertNull(userAuthenticator.authenticateAndGetUser(new UserId("44444"), new Password("password1")).orElse(null));
    }
}