package com.ogs.shopping.service.impl;

import com.ogs.shopping.dto.request.AddOrderItemDto;
import com.ogs.shopping.dto.request.UpdateOrderItemDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.entity.OrderItem;
import com.ogs.shopping.repository.OrderItemRepository;
import com.ogs.shopping.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    private ModelMapper modelMapper;

    @Override
    public OrderItemResponseDto saveOrderItem(AddOrderItemDto orderItem) {
        OrderItem orderItemEntity = modelMapper.map(orderItem, OrderItem.class);
        orderItemRepository.save(orderItemEntity);
        return modelMapper.map(orderItemEntity, OrderItemResponseDto.class);
    }

    @Override
    public OrderItemResponseDto updateOrderItem(UpdateOrderItemDto orderItem) {
        return null;
    }

    @Override
    public ApiResponse deleteOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems() {
        return List.of();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        return List.of();
    }
}
