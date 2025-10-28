package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    // add product
    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody AddProductDto newProduct) {
        ProductResponseDto productResponseDto = productService.addProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }// addProduct() ends

    // update product

    // delete product

    // find product by id

    // list all products


}// ProductController class ends
