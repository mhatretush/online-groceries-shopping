package com.ogs.shopping.dto.response;

import com.ogs.shopping.entity.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDto {

    private Long offerId;
    private LocalDate validFrom;
    private LocalDate validTill;
    private Double discount;
    private DiscountType discountType;
    private String code ;
    private boolean valid;

}
