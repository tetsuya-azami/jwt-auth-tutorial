package com.example.jwt_auth.domain.model.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNameTest {
    @Test
    void createInstance_正常系() {
        UserName userName = new UserName("user");
        assertEquals("user", userName.value());
    }

    @ParameterizedTest
    @CsvSource({
            ",userNameは空ではいけません", // null
            "'',userNameは空ではいけません", // 空文字
            "a,userNameは3文字以上20文字以下である必要があります", // 3文字未満
            "aaaaaaaaaaaaaaaaaaaaa,userNameは3文字以上20文字以下である必要があります", // 20文字超え
    })
    void createInstance_異常系(String value, String expectedMessage) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new UserName(value), expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}