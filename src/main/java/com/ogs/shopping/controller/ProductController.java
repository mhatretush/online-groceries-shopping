package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.service.impl.ProductServiceImpl;
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
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody AddProductDto newProduct) {
        ProductResponseDto productResponseDto = productService.addProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }// addProduct() ends

    // update product
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId, AddProductDto productDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productId,productDto);
        return ResponseEntity.ok(productResponseDto);
    }// updateProduct() ends

    // delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("deleted successfully");
    }// deleteProduct() ends

    // find product by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.findProductById(productId);
        return ResponseEntity.ok().body(productResponseDto);
    }// getProductById() ends

    // list all products
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productResponseDtos = productService.getAllProducts();
        return ResponseEntity.ok().body(productResponseDtos);
    }// getAllProducts() ends


}// ProductController class ends
