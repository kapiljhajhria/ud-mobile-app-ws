package com.karg.userrsmanagement.service;

import com.karg.userrsmanagement.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);
}