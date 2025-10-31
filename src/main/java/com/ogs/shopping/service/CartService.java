package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.CartResponseDto;
import com.ogs.shopping.entity.User;

public interface CartService {
    CartResponseDto addToCart(AddToCartDto addToCartDto);
    ApiResponse removeFromCart(Long userId, Long productId);
    CartResponseDto viewCart(Long userId);
    CartResponseDto applyDiscount(Long userId, String offerCode);
}