package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddOrderItemDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    //Order Product
    OrderResponseDto addOrder(AddOrderItemDto addOrderItemDto);
    List<OrderResponseDto> getOrdersByUserId(Long userId);
    ApiResponse cancelOrder(Long orderId);
}
