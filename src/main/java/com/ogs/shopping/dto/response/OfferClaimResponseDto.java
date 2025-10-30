package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter


public class OfferClaimResponseDto {
    private Long offerClaimId;
    private Long userId;
    private String offerCode;
    private LocalDate claimedAt;
    private boolean successful;
}
