package com.example.jwt_auth.application.calculation;

import com.example.jwt_auth.application.authentication.UserInfoIF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Calculator {

    public void plusNum(UserInfoIF userInfo, int numberToAdd) {
        System.out.printf("plusNum: %s, %d%n", userInfo, numberToAdd);
    }
}
