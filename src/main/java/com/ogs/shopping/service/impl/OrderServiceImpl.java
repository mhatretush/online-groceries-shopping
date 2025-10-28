package com.ogs.shopping.service.impl;

import com.ogs.shopping.dto.request.AddOrderItemDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.Order;
import com.ogs.shopping.repository.OrderRepository;
import com.ogs.shopping.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponseDto addOrder(AddOrderItemDto addOrderItemDto) {
        Order order =  modelMapper.map(addOrderItemDto, Order.class);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public ApiResponse cancelOrder(Long orderId) {
        return null;
    }
}
