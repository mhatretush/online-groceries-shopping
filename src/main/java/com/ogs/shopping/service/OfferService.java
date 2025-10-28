package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.OffeRequestDto;
import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.entity.Offer;

public interface OfferService {
   OfferResponseDto createOffer(OffeRequestDto dto);
    Offer getOfferById(Long offerId);
    OfferResponseDto updateOffer(Long offerId, OffeRequestDto dto);
    void deleteOffer(Long offerId);
    boolean isOfferValid(String code);
    Double applyOffer(String code,Double offerAmount);
}
