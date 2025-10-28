package com.ogs.shopping.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @NotBlank(message = "please enter your email address")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
