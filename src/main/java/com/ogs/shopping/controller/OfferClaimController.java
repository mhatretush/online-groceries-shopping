package com.ogs.shopping.controller;

import com.ogs.shopping.dto.response.OfferClaimResponseDto;
import com.ogs.shopping.entity.OfferClaim;
import com.ogs.shopping.repository.OfferClaimRepository;
import com.ogs.shopping.service.OfferClaimService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offer-claims")
public class OfferClaimController {
    private final OfferClaimService offerClaimService;


    @PostMapping("/claim")
    public ResponseEntity<OfferClaimResponseDto> claimOffer(
            @RequestParam Long userId,
            @RequestParam String offerCode) {
        OfferClaimResponseDto claimResponse = offerClaimService.claimOffer(userId, offerCode);
        return ResponseEntity.ok(claimResponse);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OfferClaimResponseDto>> getClaimsByUser(@PathVariable Long userId) {
        List<OfferClaimResponseDto> claims = offerClaimService.getClaimsByUser(userId);
        return ResponseEntity.ok(claims);
    }



    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<OfferClaimResponseDto>> getClaimsByOffer(@PathVariable Long offerId) {
        List<OfferClaimResponseDto> claims = offerClaimService.getClaimsByOffer(offerId);
        return ResponseEntity.ok(claims);
    }



    @GetMapping("/{claimId}")
    public ResponseEntity<OfferClaimResponseDto> getClaimById(@PathVariable Long claimId) {
        OfferClaimResponseDto claim = offerClaimService.getClaimById(claimId);
        return ResponseEntity.ok(claim);
    }



    @DeleteMapping("/{claimId}")
    public ResponseEntity<String> deleteClaim(@PathVariable Long claimId) {
        offerClaimService.deleteClaim(claimId);
        return ResponseEntity.ok("Offer claim deleted successfully");
    }

}
