package com.karg.userrsmanagement.service;

import com.karg.userrsmanagement.shared.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto user);
}