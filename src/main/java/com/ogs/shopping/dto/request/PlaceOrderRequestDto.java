package com.ogs.shopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequestDto {
    private Long userId;
    private String offerCode;
}
