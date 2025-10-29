package com.ogs.shopping.service;

<<<<<<< HEAD
import com.ogs.shopping.dto.request.OffeRequestDto;
=======
import com.ogs.shopping.dto.request.OfferRequestDto;
>>>>>>> c3c3b29e88886342670a8290dfcb97f4c544c165
import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.entity.Offer;

public interface OfferService {
<<<<<<< HEAD
   OfferResponseDto createOffer(OffeRequestDto dto);
    Offer getOfferById(Long offerId);
    OfferResponseDto updateOffer(Long offerId, OffeRequestDto dto);
    void deleteOffer(Long offerId);
    boolean isOfferValid(String code);
    Double applyOffer(String code,Double offerAmount);
=======
    public OfferResponseDto createOffer(OfferRequestDto dto);
    public Offer getOfferById(Long offerId);
    public OfferResponseDto updateOffer(Long offerId, OfferRequestDto dto);
    public void deleteOffer(Long offerId);
    public boolean isOfferValid(String code);
    public Double applyOffer(String code, Double offerAmount);

>>>>>>> c3c3b29e88886342670a8290dfcb97f4c544c165
}
