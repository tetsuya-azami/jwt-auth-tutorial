package com.example.jwt_auth.domain.model.auth;

import com.example.jwt_auth.domain.model.authentication.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserIdTest {
    @Test
    void createInstance_正常系() {
        UserId userId = new UserId("12345");
        assertEquals("12345", userId.value());
    }

    @ParameterizedTest
    @CsvSource({
            ",ユーザIDは空ではいけません", // null
            "'' ,ユーザIDは空ではいけません", // 空文字
            "1234,ユーザIDは5文字以上20文字以下である必要があります", // 5文字未満
            "123456789012345678901,ユーザIDは5文字以上20文字以下である必要があります", // 20文字超え
    })
    void createInstance_異常系(String value, String expectedMessage) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new UserId(value));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
