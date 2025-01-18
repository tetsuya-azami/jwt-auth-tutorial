package com.example.jwt_auth.domain.model.auth;

import com.example.jwt_auth.domain.model.authentication.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordTest {
    @Test
    void createInstance_正常系() {
        Password password = new Password("password");
        assertEquals("password", password.value());
    }

    @ParameterizedTest
    @CsvSource({
            ",Passwordは空ではいけません", // null
            "'',Passwordは空ではいけません", // 空文字
            "aaaaaaa,Passwordは8文字以上20文字以下である必要があります", // 8文字未満
            "aaaaaaaaaaaaaaaaaaaaa,Passwordは8文字以上20文字以下である必要があります", // 20文字超え
    })
    void createInstance_異常系(String value, String expectedMessage) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Password(value), expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}