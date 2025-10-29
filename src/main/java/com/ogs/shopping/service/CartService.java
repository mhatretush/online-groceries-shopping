package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.CartResponseDto;

public interface CartService {
    CartResponseDto addToCart(AddToCartDto addToCartDto);
    ApiResponse removeFromCart(Long userId, Long productId);
    CartResponseDto viewCart(Long userId);
}