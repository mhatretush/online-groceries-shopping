package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ProductNotFound;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.request.AddProductDto;
import com.ogs.shopping.dto.response.ProductResponseDto;
import com.ogs.shopping.entity.Product;
import com.ogs.shopping.repository.ProductRepository;
import com.ogs.shopping.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ModelMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto addProduct(AddProductDto productDto) {
        // check if product with same name exists
        productRepository.findByProductName(productDto.getProductName())
                .ifPresent(product -> {
                    throw new ResourceNotFoundException("Product with name " + productDto.getProductName() + " already exists!");
                });

        Product newProduct = mapper.map(productDto, Product.class);
        productRepository.save(newProduct);
        logger.info("Product added successfully! with id : " + newProduct.getProductId());
        return mapper.map(newProduct, ProductResponseDto.class);
    }// addProduct() ends

    @Override
    public ProductResponseDto updateProduct(Long productId, AddProductDto productDto) {
        // check if product exists
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFound("No such product exists with id :" + productId));

        mapper.map(productDto, existingProduct);
        logger.info("Product updated successfully with id : " + productId);
        return mapper.map(existingProduct, ProductResponseDto.class);
    }// updateProduct() ends

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFound("No such product exists with id :" + productId));

        productRepository.delete(existingProduct);

        logger.info("Product deleted successfully with id : " + productId);
    }// deleteProduct() ends

    @Override
    public ProductResponseDto findProductById(Long productId) {
        // find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFound("No such product exists with id :" + productId));

        // convert to dto and return
        logger.info("Product found successfully with id : {}", productId);
        return mapper.map(product, ProductResponseDto.class);
    }// findProductById() ends

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productResponseDos = new ArrayList<>();
        productList.forEach(
                product -> {
                    ProductResponseDto responseDto = mapper.map(product, ProductResponseDto.class);
                    productResponseDos.add(responseDto);
                });

        return productResponseDos;
    }// getAllProducts() ends

}//ProductServiceImpl class ends
