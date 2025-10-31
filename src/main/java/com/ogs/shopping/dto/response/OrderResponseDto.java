package com.ogs.shopping.dto.response;

import com.ogs.shopping.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate createdDateTime;
    private LocalDate modifiedDateTime;
    private List<OrderItemResponseDto> orderItems;

    private String offerCode;
    String discountType;
    private boolean offerApplied;
}