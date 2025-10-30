package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.*;
import com.ogs.shopping.repository.CartRepository;
import com.ogs.shopping.repository.OrderRepository;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.OrderService;
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

        Order order = new Order();
        order.setUser(user);

        double total = 0.0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getProductPrice()); // ✅ FIXED

            Double itemTotal = cartItem.getProduct().getProductPrice()*cartItem.getQuantity();
            total += itemTotal;

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        order.setDiscountAmount(0.0);
        order.setPayableAmount(total);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        List<OrderItemResponseDto>  orderItemResponseDtos = savedOrder.getOrderItems().stream().map(item->{
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            orderItemResponseDto.setOrderItemId(item.getOrderItemId());
            orderItemResponseDto.setProductId(item.getProduct().getProductId());
            orderItemResponseDto.setQuantity(item.getQuantity());
            orderItemResponseDto.setProductName(item.getProduct().getProductName());
            orderItemResponseDto.setPriceAtOrder(item.getPriceAtOrder());
            orderItemResponseDto.setTotalPrice(item.getQuantity()*item.getProduct().getProductPrice());
            return orderItemResponseDto;
        }).toList();

        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(savedOrder.getOrderId());
        response.setUserId(user.getUserId());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setDiscountAmount(savedOrder.getDiscountAmount());
        response.setPayableAmount(savedOrder.getPayableAmount());
        response.setOrderItems(orderItemResponseDtos);

        return response;
    }

    @Override
    public OrderResponseDto viewOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });

        List<OrderItemResponseDto> orderItemResponseDtos = order.getOrderItems().stream().map(item->{
            OrderItemResponseDto dto = new OrderItemResponseDto();
            dto.setOrderItemId(item.getOrderItemId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getProductName());
            dto.setPriceAtOrder(item.getPriceAtOrder());
            dto.setQuantity(item.getQuantity());
            dto.setTotalPrice(item.getQuantity()*item.getProduct().getProductPrice());
            return dto;
        }).toList();

        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUser().getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setDiscountAmount(order.getDiscountAmount());
        response.setPayableAmount(order.getPayableAmount());
        response.setOrderItems(orderItemResponseDtos);
        return response;
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });
        return orderRepository.findByUser(user)
                .stream()
                .map(u->{
                    return modelMapper.map(u, OrderResponseDto.class);
                }).toList();
    }
}
