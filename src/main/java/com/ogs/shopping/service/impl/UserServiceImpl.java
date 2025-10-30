package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.request.UserLoginDto;
import com.ogs.shopping.dto.request.UserRegistrationDto;
import com.ogs.shopping.dto.response.UserResponseDto;
import com.ogs.shopping.entity.Role;
import com.ogs.shopping.entity.User;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder  bCryptPasswordEncoder;

    @Override
    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        User user = modelMapper.map(userRegistrationDto, User.class);
        user.setRole(Role.CUSTOMER);
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Email not found");
                });

        if(!bCryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new ApiException("Incorrect email or password");
        }

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    return   modelMapper.map(user, UserResponseDto.class);
                }).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("User not found");
                });
        return modelMapper.map(user, UserResponseDto.class);
    }
}