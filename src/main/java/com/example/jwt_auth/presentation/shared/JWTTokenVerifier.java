package com.example.jwt_auth.presentation.shared;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt_auth.application.authentication.jwt.JWTToken;
import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.model.authentication.UserName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTTokenVerifier {

    private final JWTVerifier verifier;

    public Optional<UserInfo> verifyTokenAndGenerateUserInfo(JWTToken token) {
        if (token == null) {
            log.info("JWTトークンがnullです");
            return Optional.empty();
        }

        DecodedJWT decodedJWT;
        try {
            decodedJWT = verifier.verify(token.value());
        } catch (JWTVerificationException e) {
            // TODO: ログに秘匿情報が含まれないかチェック
            log.info("JWTトークンの検証に失敗しました", e);
            return Optional.empty();
        }

        return Optional.of(
                new UserInfo(
                        new UserId(decodedJWT.getSubject()),
                        new UserName(decodedJWT.getClaim("name").asString()))
        );
    }
}
