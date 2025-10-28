package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddOrderItemDto;
import com.ogs.shopping.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/order-items")
@Validated
public class OrderItemController {
    private OrderItemService orderItemService;

    @PostMapping("/add")
    public ResponseEntity<?> saveOrderItem(@Valid @RequestBody AddOrderItemDto orderItemDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderItemService.saveOrderItem(orderItemDto));
    }
}
