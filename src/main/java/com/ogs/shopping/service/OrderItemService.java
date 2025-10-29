package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddOrderItemDto;
import com.ogs.shopping.dto.request.UpdateOrderItemDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto saveOrderItem(AddOrderItemDto orderItem);
    OrderItemResponseDto updateOrderItem(UpdateOrderItemDto orderItem);
    ApiResponse deleteOrderItem(OrderItem orderItem);
    List<OrderItemResponseDto> getOrderItems();
    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);
}