package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private Long cartId;
    private Long userId;
    private List<CartItemResponseDto> items;

    private Double totalAmount;
}
