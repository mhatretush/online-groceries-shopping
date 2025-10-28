package com.ogs.shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ogs.shopping.entity.Order;
import jakarta.persistence.*;
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
