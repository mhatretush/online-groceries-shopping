package com.ogs.shopping.service.impl;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.repository.ProductRepository;
import com.ogs.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    private ProductRepository productRepository;

    @Override
    public ProductResponseDto addProduct(AddProductDto productDto) {
        logger.info("test");

        return null;
    }// addProduct() ends

    @Override
    public ProductResponseDto updateProduct(Long productId, AddProductDto productDto) {
        return null;
    }// updateProduct() ends

    @Override
    public void deleteProduct(Long productId) {

    }// deleteProduct() ends

    @Override
    public ProductResponseDto findProductById(Long productId) {
        return null;
    }// findProductById() ends

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return List.of();
    }// getAllProducts() ends

}//ProductServiceImpl class ends
