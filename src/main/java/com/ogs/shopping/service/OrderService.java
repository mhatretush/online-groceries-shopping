package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddOrderDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    //Order Product
    OrderResponseDto addOrder(AddOrderDto addOrderDto);
    List<OrderResponseDto> getOrdersByUserId(Long userId);
    ApiResponse cancelOrder(Long orderId);
}
