package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.UserLoginDto;
import com.ogs.shopping.dto.request.UserRegistrationDto;
import com.ogs.shopping.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);
    UserResponseDto loginUser(UserLoginDto userLoginDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
}
