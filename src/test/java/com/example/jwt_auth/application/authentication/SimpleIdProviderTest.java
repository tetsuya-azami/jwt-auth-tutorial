package com.example.jwt_auth.application.authentication;

import com.example.jwt_auth.application.authentication.result.AuthenticationFailure;
import com.example.jwt_auth.application.authentication.result.AuthenticationResult;
import com.example.jwt_auth.application.authentication.result.AuthenticationSuccess;
import com.example.jwt_auth.domain.model.authentication.*;
import com.example.jwt_auth.domain.service.authentication.JWTTokenProvider;
import com.example.jwt_auth.domain.service.authentication.UserAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class SimpleIdProviderTest {
    @InjectMocks
    private SimpleIdProvider simpleIdProvider;
    @Mock
    private UserAuthenticator userAuthenticator;
    @Mock
    private JWTTokenProvider jwtTokenProvider;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void authentication_正常系() {
        // given
        User user = new User(new UserId("11111"), new UserName("user1"), new Password("password1"));
        doReturn(Optional.of(user)).when(userAuthenticator).authenticateAndGetUser(any(), any());
        doReturn(new JWTToken("test-token")).when(jwtTokenProvider).generateToken(any());
        // when
        AuthenticationResult result = simpleIdProvider.authenticateUserAndReturnToken(new UserId("11111"), new Password("password1"));
        // then
        assertEquals(result, new AuthenticationSuccess(new JWTToken("test-token")));
    }

    @Test
    void authentication_異常系() {
        // given
        doReturn(Optional.empty()).when(userAuthenticator).authenticateAndGetUser(any(), any());
        // when
        AuthenticationResult result = simpleIdProvider.authenticateUserAndReturnToken(new UserId("11111"), new Password("password1"));
        // then
        assertEquals(result, new AuthenticationFailure("userIdまたはpasswordが間違っています"));
    }
}