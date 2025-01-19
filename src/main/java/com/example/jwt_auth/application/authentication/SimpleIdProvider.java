package com.example.jwt_auth.application.authentication;

import com.example.jwt_auth.application.authentication.result.AuthenticationFailure;
import com.example.jwt_auth.application.authentication.result.AuthenticationResult;
import com.example.jwt_auth.application.authentication.result.AuthenticationSuccess;
import com.example.jwt_auth.domain.model.authentication.Password;
import com.example.jwt_auth.domain.model.authentication.User;
import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.service.authentication.UserAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 認証と認証後のトークン払い出しをするServiceクラス
 */
@Service
@RequiredArgsConstructor
public class SimpleIdProvider {

    private final UserAuthenticator userAuthenticator;
    private final JWTTokenProvider jwtTokenProvider;

    public AuthenticationResult authenticateUserAndReturnToken(UserId userId, Password password) {
        Optional<User> userOpt = userAuthenticator.authenticateAndGetUser(userId, password);
        if (userOpt.isEmpty()) {
            return new AuthenticationFailure("userIdまたはpasswordが間違っています");
        }

        return new AuthenticationSuccess(jwtTokenProvider.generateToken(userOpt.get()));
    }
}
