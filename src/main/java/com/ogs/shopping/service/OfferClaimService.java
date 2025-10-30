package com.ogs.shopping.service;

import com.ogs.shopping.dto.response.OfferClaimResponseDto;
import com.ogs.shopping.dto.response.OfferResponseDto;

public interface OfferClaimService {
    OfferClaimResponseDto claimOffer(Long id, String code);
}
