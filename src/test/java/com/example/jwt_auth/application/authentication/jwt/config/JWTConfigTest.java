package com.example.jwt_auth.application.authentication.jwt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.jwt_auth.application.authentication.jwt.JWTTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JWTConfigTest {

    @Test
    void jwtVerifier_インスタンス化_正常系() {
        // given
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JWTConfig.class);
        JWTVerifier verifier = context.getBean(JWTVerifier.class);

        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer(JWTTokenProvider.class.getName())
                .withSubject("userId")
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .withIssuedAt(Instant.now())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", "userName")
                .sign(alg);
        // when & then
        assertDoesNotThrow(() -> verifier.verify(token));
    }

    @Test
    void jwtVerifier_issuerが異なる() {
        // given
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JWTConfig.class);
        JWTVerifier verifier = context.getBean(JWTVerifier.class);

        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("InvalidIssuer") // 期待値と異なる
                .withSubject("userId")
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .withIssuedAt(Instant.now())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", "userName")
                .sign(alg);
        // when & then
        assertThrows(JWTVerificationException.class, () -> verifier.verify(token));
    }

    @Test
    void jwtVerifier_トークンが期限切れ() {
        // given
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JWTConfig.class);
        JWTVerifier verifier = context.getBean(JWTVerifier.class);

        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer(JWTTokenProvider.class.getName())
                .withSubject("userId")
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.HOURS)) // 期限切れに設定
                .withIssuedAt(Instant.now().minus(1, ChronoUnit.HOURS))
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", "userName")
                .sign(alg);
        // when & then
        assertThrows(JWTVerificationException.class, () -> verifier.verify(token));
    }
}