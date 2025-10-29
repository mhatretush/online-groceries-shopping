package com.ogs.shopping.service.impl;


import com.ogs.shopping.dto.request.OfferRequestDto;

import com.ogs.shopping.dto.request.OfferRequestDto;
import com.ogs.shopping.dto.request.OfferRequestDto;

import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.entity.DiscountType;
import com.ogs.shopping.entity.Offer;
import com.ogs.shopping.repository.OfferRepository;
import com.ogs.shopping.service.OfferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Transactional
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final ModelMapper mapper;

    @Override

    public OfferResponseDto createOffer(OfferRequestDto dto) {

        Offer offer=mapper.map( dto, Offer.class);
        offer.setValid(true);
        offerRepository.save(offer);
        return mapper.map(offer, OfferResponseDto.class);
    }

    @Override
    public Offer getOfferById(Long offerId) {

        return   offerRepository.findById(offerId)
                .orElseThrow(()->new RuntimeException("offer not found") );


    }

    @Override

    public OfferResponseDto updateOffer(Long offerId, OfferRequestDto dto) {

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
       mapper.map(offer,Offer.class);
       Offer updatedOffer=offerRepository.save(offer);
        return mapper.map(updatedOffer,OfferResponseDto.class);
    }

    @Override
    public void deleteOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));

        offerRepository.delete(offer);
    }

    @Override
    public boolean isOfferValid(String code) {
        Offer offer = offerRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid offer code: " + code));

        LocalDate today = LocalDate.now();

        boolean withinDateRange =
                (offer.getValidFrom().isBefore(today) || offer.getValidFrom().isEqual(today)) &&
                        (offer.getValidTill().isAfter(today) || offer.getValidTill().isEqual(today));

        return offer.isValid() && withinDateRange;
    }

    @Override
    public Double applyOffer(String code, Double offerAmount) {
        Offer offer = offerRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid offer code: " + code));

        // Check validity
        if (!isOfferValid(code)) {
            throw new RuntimeException("Offer is expired or inactive: " + code);
        }

        Double discountAmount = 0.0;

        if (offer.getDiscountType() == DiscountType.FLAT) {
            discountAmount = offer.getDiscount();
        } else if (offer.getDiscountType() == DiscountType.PERCENTAGE) {
            discountAmount = (offerAmount * offer.getDiscount()) / 100;
        }

        // Ensure discount doesn’t exceed total
        if (discountAmount > offerAmount) discountAmount = offerAmount;

        // Return final amount after discount
        return offerAmount - discountAmount;
    }
}
