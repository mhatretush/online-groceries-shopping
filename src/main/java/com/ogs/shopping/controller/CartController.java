package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.entity.Cart;
import com.ogs.shopping.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
@Validated
public class CartController {
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartDto addToCartDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.addToCart(addToCartDto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(Long userId, Long productId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.removeFromCart(userId,productId));
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart(Long userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.viewCart(userId));
    }
}
