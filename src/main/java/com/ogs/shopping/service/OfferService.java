package com.ogs.shopping.service;


import com.ogs.shopping.dto.request.OfferRequestDto;

import com.ogs.shopping.dto.request.OfferRequestDto;

import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.entity.Offer;

public interface OfferService {



    public OfferResponseDto createOffer(OfferRequestDto dto);
    public OfferResponseDto getOfferById(Long offerId);
    public OfferResponseDto updateOffer(Long offerId, OfferRequestDto dto);
    public void deleteOffer(Long offerId);
    public boolean isOfferValid(String code);
    public Double applyOffer(String code, Double offerAmount);


}
