package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.OfferRequestDto;
import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.service.OfferService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/offer",produces = "application/json")

public class OfferController {
    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponseDto> createOffer(@RequestBody OfferRequestDto dto) {
        OfferResponseDto createOffer = offerService.createOffer(dto);
        return ResponseEntity.ok(createOffer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable Long id ){
        OfferResponseDto offer=offerService.getOfferById(id);

        return ResponseEntity.ok(offer);
    }

    @PutMapping("/update/{offerId}")
    public ResponseEntity<OfferResponseDto> updteOffer(@PathVariable Long offerId, @RequestBody OfferRequestDto dto){
        OfferResponseDto offer=offerService.updateOffer(offerId,dto);
        return ResponseEntity.ok(offer);
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long offerId){
        offerService.deleteOffer(offerId);
        return ResponseEntity.ok("the offer has been deleted successfully");
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<Boolean> isOfferValid(@PathVariable String code){
       boolean valid =offerService.isOfferValid(code);
       return  ResponseEntity.ok(valid);
    }

    @GetMapping("/apply")
    public ResponseEntity<Double> applyOffer(@RequestParam String code, @RequestParam Double amount){
        Double discountedAmount=offerService.applyOffer(code,amount);
        return ResponseEntity.ok(discountedAmount);
    }


}
