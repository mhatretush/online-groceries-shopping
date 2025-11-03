package com.ogs.shopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscountRequestDto {
    private Long userId;
    private String offerCode;
}
