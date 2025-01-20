package com.example.jwt_auth.application.authentication;

import com.example.jwt_auth.domain.model.authentication.UserId;
import com.example.jwt_auth.domain.model.authentication.UserName;

public interface UserInfoIF {
    UserId userId();

    UserName userName();
}
