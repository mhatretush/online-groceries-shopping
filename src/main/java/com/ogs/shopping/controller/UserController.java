package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.UserLoginDto;
import com.ogs.shopping.dto.request.UserRegistrationDto;
import com.ogs.shopping.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    //REGISTER USER
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(userRegistrationDto));
    }

    //USER LOGIN
    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.loginUser(userLoginDto));
    }

    //GET ALL USERS
    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    //GET USER BY USER ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(userId));
    }
}
