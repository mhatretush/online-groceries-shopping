package com.ogs.shopping.dto.request;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {

    private String productName;

    private double productPrice;

    private int productQty;

}// AddProductDto class ends
