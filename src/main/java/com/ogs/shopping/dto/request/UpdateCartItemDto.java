package com.ogs.shopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDto {
    private Long productId;
    private int quantity;
}