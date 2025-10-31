package com.ogs.shopping.service;

import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    //Order Product
    OrderResponseDto placeOrder(Long userId, String offerCode);
    OrderResponseDto viewOrder(Long orderId);
    List<OrderResponseDto> getUserOrders(Long userId);
    ApiResponse updateOrderStatus(Long orderId, OrderStatus newStatus);
}
