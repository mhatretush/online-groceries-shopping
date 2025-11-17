package com.ogs.shopping.dto.request;

import com.ogs.shopping.entity.LimitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPriceLimitDto {
     private LimitType limitType;
     private double value;
}
