package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse {

    private LocalDateTime timestamp;
    private String message;

    public ApiResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}