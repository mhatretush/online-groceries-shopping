package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.*;
import com.ogs.shopping.repository.CartRepository;
import com.ogs.shopping.repository.OrderRepository;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.OrderService;
import com.ogs.shopping.utils.OrderMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private OrderMapper orderMapper;

    @Override
    public OrderResponseDto placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                   throw new ResourceNotFoundException("Order Not Found");
                });
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Cart Not Found");
                });

        if(cart.getItems().isEmpty()){
            throw new ResourceNotFoundException("Cart is Empty cannot place Order");
        }

        Order order = orderMapper.toOrder(user, cart.getItems());
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        OrderResponseDto response = orderMapper.toOrderResponseDto(savedOrder);
        return response;
    }

    @Override
    public OrderResponseDto viewOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });
        return orderMapper.toOrderResponseDto(order);
    }


    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });
        return orderRepository.findByUserOrderByOrderDateDesc(user)
                .stream()
                .map(orderMapper::toOrderResponseDto)
                .toList();
    }
}