package com.example.jwt_auth.presentation.authentication;

import com.example.jwt_auth.application.authentication.AuthenticationFailure;
import com.example.jwt_auth.application.authentication.AuthenticationResult;
import com.example.jwt_auth.application.authentication.AuthenticationSuccess;
import com.example.jwt_auth.application.authentication.SimpleIdProvider;
import com.example.jwt_auth.domain.model.authentication.Password;
import com.example.jwt_auth.domain.model.authentication.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final SimpleIdProvider simpleIdProvider;

    @RequestMapping(value = "/authentication", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        UserId userId = new UserId(request.userId());
        Password password = new Password(request.password());
        
        AuthenticationResult result = simpleIdProvider.authenticateUserAndReturnToken(userId, password);

        // TODO: logging
        return switch (result) {
            case AuthenticationSuccess authenticationSuccess -> ResponseEntity
                    .ok()
                    .body(new AuthenticationResponse.Success(authenticationSuccess.token().value()));
            case AuthenticationFailure authenticationFailure -> ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse.Failure(authenticationFailure.errorMessage()));
        };
    }
}