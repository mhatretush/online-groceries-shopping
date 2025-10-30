package com.ogs.shopping.service.impl;

import com.ogs.shopping.dto.response.OfferClaimResponseDto;

import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.dto.response.UserResponseDto;

import com.ogs.shopping.entity.Offer;
import com.ogs.shopping.entity.OfferClaim;
import com.ogs.shopping.entity.User;
import com.ogs.shopping.repository.OfferClaimRepository;
import com.ogs.shopping.repository.OfferRepository;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.OfferClaimService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.PrivateKey;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class OfferClaimServiceImpl implements OfferClaimService {
    private final UserRepository userRepository;
    private final OfferClaimRepository offerClaimRepository;
    private final OfferRepository offerRepository;
    private final ModelMapper mapper;
    @Override

    public OfferClaimResponseDto claimOffer(Long id, String code) {
        User user= userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user does not exist by this id "));

        Offer offer= offerRepository.findByCode(code)
                .orElseThrow(()->new RuntimeException("offer code is not valid "));

        LocalDate today=LocalDate.now();
        boolean isValid= offer.isValid() &&
                (offer.getValidFrom().isBefore(today) ||offer.getValidFrom().isEqual(today) ) &&
                (offer.getValidTill().isAfter(today) || offer.getValidTill().isAfter(today));


        if (!isValid) {
            throw new RuntimeException("Offer is expired or invalid.");
        }

        if (offerClaimRepository.existsByUserAndOffer(user, offer)) {
            throw new RuntimeException("You have already claimed this offer.");



        offerClaimRepository.save(offerClaim);

        return mapper.map(offerClaim, OfferClaimResponseDto.class);
    }


    @Override
    public List<OfferClaimResponseDto> getClaimsByUser(Long userId) {
        List<OfferClaim> claims = offerClaimRepository.findByUser_UserId(userId);
        return claims.stream()
                .map(claim -> mapper.map(claim, OfferClaimResponseDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<OfferClaimResponseDto> getClaimsByOffer(Long offerId) {
        List<OfferClaim> claims = offerClaimRepository.findByOffer_OfferId(offerId);
        return claims.stream()
                .map(claim -> mapper.map(claim, OfferClaimResponseDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public OfferClaimResponseDto getClaimById(Long claimId) {
        OfferClaim claim = offerClaimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        return mapper.map(claim, OfferClaimResponseDto.class);
    }


    @Override
    public void deleteClaim(Long claimId) {
        OfferClaim claim = offerClaimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        offerClaimRepository.delete(claim);
    }


}
