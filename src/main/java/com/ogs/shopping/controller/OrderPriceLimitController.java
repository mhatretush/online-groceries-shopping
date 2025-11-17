package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.OrderPriceLimitDto;
import com.ogs.shopping.entity.LimitType;
import com.ogs.shopping.entity.OrderPriceLimit;
import com.ogs.shopping.service.impl.OrderPriceLimitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price-limit")
@AllArgsConstructor
public class OrderPriceLimitController {

    private final OrderPriceLimitService service;

    @PostMapping("/add")
    public ResponseEntity<?> addLimit(@RequestBody OrderPriceLimitDto dto) {
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addLimit(dto));
    }

    @PutMapping("/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderPriceLimit updateLimit(@PathVariable LimitType type, @RequestParam double value) {
        return service.updateLimit(type, value);
    }

    @GetMapping("/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public double getLimit(@PathVariable LimitType type) {
        return service.getLimit(type);
    }
}
