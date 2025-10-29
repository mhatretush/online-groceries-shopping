package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.payload.ApiResponse;
import com.ogs.shopping.service.impl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    // add product
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(@RequestBody AddProductDto newProduct) {
        ProductResponseDto productResponseDto = productService.addProduct(newProduct);
        return new ResponseEntity<>(ApiResponse.success("Product added successfully",productResponseDto),HttpStatus.CREATED);
    }// addProduct() ends

    // update product
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@PathVariable Long productId, AddProductDto productDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productId,productDto);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully",productResponseDto));
    }// updateProduct() ends

    // delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully",null));
    }// deleteProduct() ends

    // find product by id
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.findProductById(productId);
        return ResponseEntity.ok(ApiResponse.success("Product fetched successfully",productResponseDto));
    }// getProductById() ends

    // list all products
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> productResponseDtos = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("Product list fetched successfully",productResponseDtos));
    }// getAllProducts() ends


}// ProductController class ends
