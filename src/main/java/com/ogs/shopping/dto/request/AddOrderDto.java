package com.ogs.shopping.dto.request;

import com.ogs.shopping.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrderDto {
    private Long userId;
    private Product product;
}
