package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    //Order Product
    OrderResponseDto placeOrder(Long userId);
    OrderResponseDto viewOrder(Long orderId);
    List<OrderResponseDto> getUserOrders(Long userId);
}
