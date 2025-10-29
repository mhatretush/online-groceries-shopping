package com.ogs.shopping.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "please enter your email address")
    @Email(message = "please enter valid email address")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
