package com.blogify.api.service;

import com.blogify.api.domain.dto.UserDto;
import com.blogify.api.domain.dto.UserRegistrationRequest;

public interface UserService {
    UserDto registerUser(UserRegistrationRequest registrationRequest);
}