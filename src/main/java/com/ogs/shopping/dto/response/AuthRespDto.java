package com.ogs.shopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRespDto {
    private Long id;
    private String token;
    private String email;
    private String name;
    private String role;
}
