package com.ogs.shopping.custom_exception;

@Deprecated
public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message) {
        super(message);
    }
}// ProductNotFound class ends
