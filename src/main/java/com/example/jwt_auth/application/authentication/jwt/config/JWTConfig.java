package com.example.jwt_auth.application.authentication.jwt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.jwt_auth.application.authentication.jwt.JWTTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(Algorithm.HMAC256("secret"))
                .withIssuer(JWTTokenProvider.class.getName())
                .acceptExpiresAt(5)
                .build();
    }
}
