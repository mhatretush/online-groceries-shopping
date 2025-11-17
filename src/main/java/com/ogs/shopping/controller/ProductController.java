package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.payload.ApiResponse;
import com.ogs.shopping.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;

    // add product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(@Valid @RequestBody AddProductDto newProduct) {
        ProductResponseDto productResponseDto = productService.addProduct(newProduct);
        return new ResponseEntity<>(ApiResponse.success("Product added successfully",productResponseDto),HttpStatus.CREATED);
    }// addProduct() ends

    // update product
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@Valid @PathVariable Long productId, AddProductDto productDto) {
        log.info("Update product with id {}", productId);

        ProductResponseDto productResponseDto = productService.updateProduct(productId,productDto);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully",productResponseDto));
    }// updateProduct() ends

    // delete product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        log.info("Delete product with id:{}",productId);
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully",null));
    }// deleteProduct() ends

    // find product by id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long productId) {
        log.debug("REST request to get product : {}", productId);
        ProductResponseDto productResponseDto = productService.findProductById(productId);

        // add HATEOAS links
        // link to self
        productResponseDto.add(
                linkTo(methodOn(ProductController.class).getProductById(productId)).withRel("Link to self")
        );
        // link to getAllProducts()
        productResponseDto.add(
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()
        );

        // link to deleteProduct()
        productResponseDto.add(
                linkTo(methodOn(ProductController.class).deleteProduct(productId)).withRel("delete-product")
        );

        return ResponseEntity.ok(ApiResponse.success("Product fetched successfully",productResponseDto));
    }// getProductById() ends

    // list all products
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        log.debug("REST request to get all products");
        List<ProductResponseDto> productResponseDtos = productService.getAllProducts();
        log.info("Fetched {} products ", productResponseDtos.size());
        return ResponseEntity.ok(ApiResponse.success("Product list fetched successfully",productResponseDtos));
    }// getAllProducts() ends


}// ProductController class ends
