package com.ogs.shopping.dto.response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;

    private String productName;

    private double productPrice;

    private int productQty;
}// ProductResponseDto class ends
