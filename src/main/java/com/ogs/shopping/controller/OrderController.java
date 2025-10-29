package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddToCartDto;
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
    public ResponseEntity<?> placeOrder(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(userId));
    }

    @GetMapping("/view/{orderId}")
    public ResponseEntity<?> viewOrder(Long orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.viewOrder(orderId));
    }

    @GetMapping("/users/userId")
    public ResponseEntity<?> getUserOrders(Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getUserOrders(userId));
    }
}
