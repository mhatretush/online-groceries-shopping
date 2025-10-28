package com.ogs.shopping.payload;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object error;

    // success response
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }// success() ends

    // error response
    public static <T> ApiResponse<T> error(String message, Object error) {
        return new ApiResponse<>(false,message,null,error);
    }// error() ends

}// ApiResponse class ends
