package com.ogs.shopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
        private Long orderItemId;
        private Long productId;
        private String productName;
        private Double priceAtOrder;
        private Integer quantity;
        private Double totalPrice;
}

