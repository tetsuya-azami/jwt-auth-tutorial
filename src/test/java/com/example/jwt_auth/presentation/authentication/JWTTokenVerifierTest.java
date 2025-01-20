package com.example.jwt_auth.presentation.authentication;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt_auth.application.authentication.jwt.JWTToken;
import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.model.authentication.UserName;
import com.example.jwt_auth.presentation.shared.JWTTokenVerifier;
import com.example.jwt_auth.presentation.shared.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class JWTTokenVerifierTest {
    @InjectMocks
    private JWTTokenVerifier verifier;
    @Mock
    private JWTVerifier mockVerifier;
    @Mock
    private DecodedJWT mockDecodedJWT;
    @Mock
    private Claim mockedClaim;

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
    void verifyTokenAndGenerateUserInfo_正常系() {
        // given
        when(mockVerifier.verify(anyString())).thenReturn(mockDecodedJWT);
        when(mockDecodedJWT.getSubject()).thenReturn("user123");
        when(mockDecodedJWT.getClaim("name")).thenReturn(mockedClaim);
        when(mockedClaim.asString()).thenReturn("John Doe");
        JWTToken token = new JWTToken("valid.jwt.token");

        // when
        Optional<UserInfo> result = verifier.verifyTokenAndGenerateUserInfo(token);

        assertTrue(result.isPresent());
        assertEquals(new UserId("user123"), result.get().userId());
        assertEquals(new UserName("John Doe"), result.get().userName());
    }

    @Test
    void verifyTokenAndGenerateUserInfo_トークンの検証に失敗() {
        // given
        when(mockVerifier.verify("invalid.jwt.token")).thenThrow(new JWTVerificationException("invalid token"));
        JWTToken token = new JWTToken("invalid.jwt.token");

        // when
        Optional<UserInfo> result = verifier.verifyTokenAndGenerateUserInfo(token);

        // then
        assertFalse(result.isPresent());
    }

    @Test
    void verifyTokenAndGenerateUserInfo_トークンがnull() {
        JWTTokenVerifier verifier = new JWTTokenVerifier(mockVerifier);

        Optional<UserInfo> result = verifier.verifyTokenAndGenerateUserInfo(null);

        assertFalse(result.isPresent());
    }
}