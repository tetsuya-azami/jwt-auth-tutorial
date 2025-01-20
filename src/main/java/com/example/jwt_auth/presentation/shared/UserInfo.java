package com.example.jwt_auth.presentation.shared;

import com.example.jwt_auth.application.authentication.UserInfoIF;
import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.model.authentication.UserName;

public record UserInfo(UserId userId, UserName userName) implements UserInfoIF {
}
