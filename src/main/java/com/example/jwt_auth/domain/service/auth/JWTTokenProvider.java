package com.example.jwt_auth.domain.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt_auth.domain.model.auth.JWTToken;
import com.example.jwt_auth.domain.model.auth.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class JWTTokenProvider {
    public JWTToken generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("AuthTokenProducer")
                .withSubject(user.id().value())
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .withIssuedAt(Instant.now())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", user.username().value())
                .sign(algorithm);

        return new JWTToken(token);
    }
}