package com.ogs.shopping.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrderItemDto {
    @NotNull(message = "please enter userId")
    private Long userId;
    @NotNull(message = "please enter productId")
    private Long productId;
    @NotNull(message = "please enter the quantity")
    private int quantity;
}