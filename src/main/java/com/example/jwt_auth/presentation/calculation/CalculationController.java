package com.example.jwt_auth.presentation.calculation;

import com.example.jwt_auth.application.authentication.jwt.JWTToken;
import com.example.jwt_auth.application.calculation.Calculator;
import com.example.jwt_auth.presentation.shared.JWTTokenVerifier;
import com.example.jwt_auth.presentation.shared.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// TODO: テストを書く
@RestController
@RequestMapping("/api/v1/calculation")
@RequiredArgsConstructor
public class CalculationController {
    private final JWTTokenVerifier jwtTokenVerifier;
    private final Calculator calculator;

    @PostMapping(value = "/plus", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CalculationResponse> plus(@RequestHeader("Authorization") String authorization, @RequestBody CalculationRequest request) {
        Optional<UserInfo> userInfo = jwtTokenVerifier.verifyTokenAndGenerateUserInfo(new JWTToken(authorization));
        if (userInfo.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new CalculationResponse.Failure("AUTHORIZATION_ERROR", "権限がありません"));
        }

        if (request == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new CalculationResponse.Failure("INVALID_REQUEST", "Request body is empty"));
        }

        calculator.plusNum(userInfo.get(), request.numberToAdd());

        return ResponseEntity.ok(new CalculationResponse.Success("OK", "計算が完了しました"));
    }
}
