package com.ogs.shopping.utils;

import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderMapper {

    public Order toOrder(User user, List<CartItem> cartItems){
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        double total = 0.0;

        List<OrderItem> orderItems = cartItems.stream().map(cartItem ->  {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getProductPrice());

            return orderItem;
        }).toList();
        total = orderItems.stream().mapToDouble(oi->oi.getQuantity()*oi.getPriceAtOrder())
                .sum();

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        order.setDiscountAmount(0.0);
        order.setPayableAmount(total);

        return order;
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setPayableAmount(order.getPayableAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedDateTime(order.getOrderDate());
        dto.setModifiedDateTime(order.getModifiedDate());

        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(item -> toOrderItemResponseDto(item))
                .toList();
        dto.setOrderItems(itemDtos);
        return dto;
    }

    public OrderItemResponseDto toOrderItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setOrderItemId(item.getOrderItemId());
        dto.setProductId(item.getProduct().getProductId());
        dto.setProductName(item.getProduct().getProductName());
        dto.setPriceAtOrder(item.getPriceAtOrder());
        dto.setQuantity(item.getQuantity());
        dto.setTotalPrice(item.getQuantity() * item.getPriceAtOrder());
        return dto;
    }
}
