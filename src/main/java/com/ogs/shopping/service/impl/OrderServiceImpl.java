package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.*;
import com.ogs.shopping.repository.CartRepository;
import com.ogs.shopping.repository.OrderRepository;
import com.ogs.shopping.repository.PublicHolidayRepository;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.OrderService;
import com.ogs.shopping.service.PublicHolidayService;
import com.ogs.shopping.utils.OrderMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final PublicHolidayService publicHolidayService;

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

        LocalDate today = LocalDate.now();
        DayOfWeek  dayOfWeek = today.getDayOfWeek();

        if(dayOfWeek.equals(DayOfWeek.SUNDAY)){
            throw new ApiException("Orders cannot be placed on Sunday");
        }

        if (publicHolidayService.isPublicHoliday(today)) {
            throw new ApiException("Orders cannot be placed on public holidays.");
        }

        double total = cart.getItems().stream()
                .mapToDouble(item->item.getProduct().getProductPrice()* item.getQuantity())
                .sum();

        if (total < 99) {
            throw new ApiException("Minimum order amount must be ₹99 or more");
        }

        if (total > 4999) {
            throw new ApiException("Maximum order amount should not exceed ₹4999");
        }

        Order order = orderMapper.toOrder(user, cart.getItems());
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toOrderResponseDto(savedOrder);
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