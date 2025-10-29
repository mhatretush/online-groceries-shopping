package com.ogs.shopping.exception_handler;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ProductNotFound;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Something went wrong on the server"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> rejectedFields = e.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(rejectedFields);
    }

    //=================================================================================================================
    //product related exceptions
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<com.ogs.shopping.payload.ApiResponse<Void>> handleProductNotFound(ProductNotFound e){
        com.ogs.shopping.payload.ApiResponse<Void> apiResponse = com.ogs.shopping.payload.ApiResponse.error("No such product found",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }// handleProductNotFound()
    

}
