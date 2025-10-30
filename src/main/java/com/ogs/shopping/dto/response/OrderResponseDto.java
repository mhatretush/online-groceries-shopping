package com.ogs.shopping.dto.response;

import com.ogs.shopping.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private Double discountAmount;
    private Double payableAmount;
    private OrderStatus status;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private List<OrderItemResponseDto> orderItems;
}