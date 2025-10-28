package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto addProduct(AddProductDto productDto);
    ProductResponseDto updateProduct(Long productId,AddProductDto productDto);
    void deleteProduct(Long productId);
    ProductResponseDto findProductById(Long productId);
    List<ProductResponseDto> getAllProducts();
    
}// ProductService interface ends
