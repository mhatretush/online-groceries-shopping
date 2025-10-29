package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long orderItemId;
    private Long userId;
    private Long orderId;
    private String productName;
    private int quantity;
    private Double priceAtOrder;
}
