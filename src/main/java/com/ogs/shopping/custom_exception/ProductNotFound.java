package com.ogs.shopping.custom_exception;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message) {
        super(message);
    }
}// ProductNotFound class ends
