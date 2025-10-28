package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private String productName;
    private double productPrice;
    private int productQty;
    private double totalAmount;
    private double discountAmount;
    private double payableAmount;
}
