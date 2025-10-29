package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ProductNotFound;
import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.entity.Product;
import com.ogs.shopping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // enables Mockito without starting spring
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository; // create fake repository

    @Mock
    private ModelMapper modelMapper; // create fake model mapper

    @InjectMocks
    private ProductServiceImpl productService; // create real service with above fakes injected

    private AddProductDto addProductDto;
    private Product product;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        addProductDto = new AddProductDto();
        addProductDto.setProductName("Laptop");
        addProductDto.setProductPrice(50000.0);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setProductPrice(50000.0);

        productResponseDto = new ProductResponseDto();
        productResponseDto.setProductId(1L);
        productResponseDto.setProductName("Laptop");
        productResponseDto.setProductPrice(50000.0);
    }// setUp() ends

    // ====== Testing addProduct() ======

    @Test
    @DisplayName("Should add product successfully when product name is unique")
    void shouldAddProductSuccessfullyWhenProductNameIsUnique() {
        // (1) Given - Product name doesn't exist
        when(productRepository.findByProductName(addProductDto.getProductName()))
                .thenReturn(Optional.empty()); // check if no duplicates exist

        // check  mock model mapper to convert dto to entity
        when(modelMapper.map(addProductDto, Product.class))
                .thenReturn(product);

        // check mock repository save method
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // mock modelMapper toconvert entity to responseDto
        when(modelMapper.map(product, ProductResponseDto.class))
                .thenReturn(productResponseDto);

        // (2) when - call the service method
        ProductResponseDto result = productService.addProduct(addProductDto);

        // (3) Then - verify the results
        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        assertEquals("Laptop", result.getProductName());
        assertEquals(50000.0, result.getProductPrice());

        // (4) verify interactions
        verify(productRepository,times(1)).findByProductName("Laptop");
        verify(productRepository,times(1)).save(any(Product.class));
        verify(modelMapper,times(1)).map(product, ProductResponseDto.class);
        verify(modelMapper,times(1)).map(addProductDto, Product.class);

    }// shouldAddProductSuccessfullyWhenProductNameIsUnique

    @Test
    @DisplayName("should throw exception when adding duplicate product name")
    void shouldThrowExceptionWhenProductNameIsDuplicate() {
        // (1) Given -> product with name that already exists
        Product existingProduct = new Product();
        existingProduct.setProductName("Laptop");
        existingProduct.setProductId(2L);

        when(productRepository.findByProductName(existingProduct.getProductName()))
                .thenReturn(Optional.of(existingProduct));

        // (2) - (3) When & Then verify that exception is thrown
        ProductNotFound exception = assertThrows(ProductNotFound.class, () -> {
           productService.addProduct(addProductDto);
        });

        assertEquals("Product with name Laptop already exists!", exception.getMessage());

        // (4) verify that save() was never called
        verify(productRepository,never()).save(any(Product.class));

    }// shouldThrowExceptionWhenProductNameIsDuplicate

    @Test
    @DisplayName("Should return empty list when no products exist")
    void shouldReturnEmptyListWhenNoProductsExist() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<ProductResponseDto> result = productService.getAllProducts();

        assertTrue(result.isEmpty());
    }// shouldReturnEmptyListWhenNoProductsExist() ends

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void findProductById() {
    }

    @Test
    void getAllProducts() {
    }
}// ProductServiceImplTest