package com.example.jwt_auth.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt_auth.domain.model.auth.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JWTTokenProviderTest {
    private static final Instant TOKEN_PUBLISHED_INSTANT = Instant.now().minusSeconds(5);
    private static final UUID UUID = java.util.UUID.fromString("42550138-1fd5-4dde-b799-ea20134f733f");
    private static final MockedStatic<Instant> mockedInstant = Mockito.mockStatic(Instant.class, Mockito.CALLS_REAL_METHODS);
    private static final MockedStatic<java.util.UUID> mockedUUID = Mockito.mockStatic(UUID.class);
    private JWTTokenProvider jwtTokenProvider;

    @BeforeAll
    static void setUp() {
        mockedInstant.when(Instant::now).thenReturn(TOKEN_PUBLISHED_INSTANT);
        mockedUUID.when(java.util.UUID::randomUUID).thenReturn(UUID);
    }

    @AfterAll
    static void close() {
        mockedInstant.close();
        mockedUUID.close();
    }

    @BeforeEach
    void setUpEach() {
        jwtTokenProvider = new JWTTokenProvider();
    }

    @Test
    void generateToken() {
        // given
        User user = new User(new UserId("11111"), new UserName("user1"), new Password("password"));
        // when
        JWTToken jwtToken = jwtTokenProvider.generateToken(user);
        // then
        assert_JWTトークンが予期したものであること(jwtToken, user);
    }

    private void assert_JWTトークンが予期したものであること(JWTToken actual, User expectedUser) {
        String token = actual.value();
        Algorithm alg = Algorithm.HMAC256("secret");
        DecodedJWT decodedJWT = JWT.require(alg)
                .build()
                .verify(token);

        assertEquals("AuthTokenProducer", decodedJWT.getIssuer());
        assertEquals(expectedUser.id().value(), decodedJWT.getSubject());
        assertEquals(
                TOKEN_PUBLISHED_INSTANT.truncatedTo(ChronoUnit.SECONDS),
                decodedJWT.getIssuedAtAsInstant().truncatedTo(ChronoUnit.SECONDS)
        );
        assertEquals(
                TOKEN_PUBLISHED_INSTANT.plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.SECONDS),
                decodedJWT.getExpiresAtAsInstant().truncatedTo(ChronoUnit.SECONDS)
        );
        assertEquals(UUID.toString(), decodedJWT.getId());
        assertEquals(expectedUser.username().value(), decodedJWT.getClaim("name").asString());
    }
}