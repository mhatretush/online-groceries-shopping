package com.ogs.shopping.dto.response;

import com.ogs.shopping.entity.DiscountType;

import java.time.LocalDate;

public class OfferResponseDto {

    private Long offerId;
    private LocalDate validFrom;
    private LocalDate validTill;
    private Double discount;
    private DiscountType discountType;
    private String code ;
    private boolean valid;

}
