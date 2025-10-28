package com.ogs.shopping.dto.request;

import com.ogs.shopping.entity.DiscountType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OffeRequestDto {
    @NotEmpty(message = "the offer code cannot be empty")
    private String code;

    @NotNull(message = "the validity starting date is required ")
    private LocalDate validFrom;

    @NotNull(message="the expiry date is required")
    private LocalDate validTill ;

    @Min(value =1,message = "the discount should be more than zero")
    @Max(value =100,message = "the discount value cannot exceed above 100")
    private Double discount;

    @NotNull(message = "the discount type cannot be empty")
    private DiscountType discountType;
}
