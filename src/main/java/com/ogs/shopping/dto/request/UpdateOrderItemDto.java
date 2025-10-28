package com.ogs.shopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderItemDto {
    private Long productId;
    private int quantity;
}