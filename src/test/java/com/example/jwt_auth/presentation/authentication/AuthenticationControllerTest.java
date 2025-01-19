package com.example.jwt_auth.presentation.authentication;

import com.example.jwt_auth.application.authentication.SimpleIdProvider;
import com.example.jwt_auth.application.authentication.jwt.JWTToken;
import com.example.jwt_auth.application.authentication.jwt.result.AuthenticationFailure;
import com.example.jwt_auth.application.authentication.jwt.result.AuthenticationSuccess;
import com.example.jwt_auth.domain.model.authentication.Password;
import com.example.jwt_auth.domain.model.authentication.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {
    @MockitoBean
    private SimpleIdProvider simpleIdProvider;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void authentication_正常系() throws Exception {
        // given
        AuthenticationSuccess authenticationResult = new AuthenticationSuccess(new JWTToken("dummyToken"));
        doReturn(authenticationResult)
                .when(simpleIdProvider).authenticateUserAndReturnToken(new UserId("11111"), new Password("password1"));
        // when & then
        mockMvc.perform(post("/api/v1/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "userId": "11111",
                                     "password": "password1"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {"token":"dummyToken"}
                                """));
    }

    @Test
    void authentication_認証失敗() throws Exception {
        // given
        AuthenticationFailure authenticationResult = new AuthenticationFailure("認証失敗です");
        doReturn(authenticationResult)
                .when(simpleIdProvider).authenticateUserAndReturnToken(new UserId("11111"), new Password("password1"));
        // when & then
        mockMvc.perform(post("/api/v1/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "userId": "11111",
                                     "password": "password1"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                    "errorMessage": "認証失敗です"
                                }
                                """));
    }
}