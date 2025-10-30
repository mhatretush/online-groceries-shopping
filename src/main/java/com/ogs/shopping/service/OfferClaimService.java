package com.ogs.shopping.service;

import com.ogs.shopping.dto.response.OfferClaimResponseDto;
import com.ogs.shopping.dto.response.OfferResponseDto;

import java.util.List;

public interface OfferClaimService {
    OfferClaimResponseDto claimOffer(Long userId, String offerCode);


    List<OfferClaimResponseDto> getClaimsByUser(Long userId);


    List<OfferClaimResponseDto> getClaimsByOffer(Long offerId);


    OfferClaimResponseDto getClaimById(Long claimId);


    void deleteClaim(Long claimId);
}
