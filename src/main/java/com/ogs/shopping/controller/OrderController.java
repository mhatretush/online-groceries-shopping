package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.OrderStatus;
import com.ogs.shopping.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long userId, String offerCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.placeOrder(userId, offerCode));
    }

    @GetMapping("/view/{orderId}")
    public ResponseEntity<?> viewOrder(Long orderId) {

        OrderResponseDto orderResponseDto = orderService.viewOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.viewOrder(orderId));
    }

    @GetMapping("/users/userId")
    public ResponseEntity<?> getUserOrders(Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getUserOrders(userId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(orderService.updateOrderStatus(orderId, newStatus));
    }

}
