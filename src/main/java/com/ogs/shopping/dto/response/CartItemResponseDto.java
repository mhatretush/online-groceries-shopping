package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Double productPrice;
    private int quantity;
    private Double totalPrice;
}
