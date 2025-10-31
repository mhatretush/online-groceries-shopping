package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.OfferRequestDto;
import com.ogs.shopping.payload.ApiResponse;
import com.ogs.shopping.dto.response.OfferResponseDto;
import com.ogs.shopping.service.OfferService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/offer",produces = "application/json")

public class OfferController {
    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<ApiResponse<OfferResponseDto>>  createOffer(@RequestBody OfferRequestDto dto) {
        OfferResponseDto createOffer = offerService.createOffer(dto);
        return new ResponseEntity<>(ApiResponse.success("Offer created successfully", createOffer),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OfferResponseDto>> getOfferById(@PathVariable Long id ){
        OfferResponseDto offer=offerService.getOfferById(id);

        return new ResponseEntity<>(ApiResponse.success("offer by id fetched succesfully",offer),HttpStatus.OK);
    }

    @PutMapping("/update/{offerId}")
    public ResponseEntity<ApiResponse<OfferResponseDto>> updteOffer(@PathVariable Long offerId, @RequestBody OfferRequestDto dto){
        OfferResponseDto offer=offerService.updateOffer(offerId,dto);
        return new ResponseEntity<>(ApiResponse.success("offer has been updated successfully",offer),HttpStatus.OK);
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<ApiResponse<String>> deleteOffer(@PathVariable Long offerId){
        offerService.deleteOffer(offerId);
        return new ResponseEntity<>(ApiResponse.success("offer has been deleted succesfully",null),HttpStatus.OK);
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<ApiResponse<Boolean>> isOfferValid(@PathVariable String code){
       boolean valid =offerService.isOfferValid(code);
       return  new ResponseEntity<>(ApiResponse.success("offer is valid",valid),HttpStatus.OK);
    }

    @GetMapping("/apply")
    public ResponseEntity<ApiResponse<Double>> applyOffer(@RequestParam String code, @RequestParam Double amount){
        Double discountedAmount=offerService.applyOffer(code,amount);
        return new ResponseEntity<>(ApiResponse.success("offer is applied succesfully",discountedAmount),HttpStatus.OK);
    }


}
