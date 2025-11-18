package com.ogs.shopping.service.impl;
import com.ogs.shopping.entity.Offer;
import com.ogs.shopping.repository.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

// write all the test cases related to OfferServiceImplTest class here
public class OfferServiceImplTest {

    @Test
    void isOfferTrue_shouldReturnTrue_whenOfferIsValidActiveAndWithinDate() {

        /*
        mock the dependencies required for OfferServiceImpl
        dependencies : OfferRepository, ModelMapper
        */
        OfferRepository mockRepo = Mockito.mock(OfferRepository.class);
        ModelMapper mockMapper = Mockito.mock(ModelMapper.class);

        // create a fake offer
        Offer sampleOffer = Offer.builder()
                .code("SALE123").
                validFrom(LocalDate.now().minusDays(1))
                .validTill(LocalDate.now().plusDays(1))
                .valid(true)
                .build();

        // when OfferRepo asks for SALE123, give back out mock offer
        Mockito.when(mockRepo.findByCode("SALE123"))
                .thenReturn(Optional.of(sampleOffer));

        OfferServiceImpl offerService = new OfferServiceImpl(mockRepo, mockMapper);
        Assertions.assertTrue(offerService.isOfferValid("SALE123"), "Offer should be valid when active and within date");

    }// isOfferTrue_shouldReturnTrue_whenOfferIsValidActiveAndWithinDate() ends

    @Test
    void isOfferTrue_shouldReturnFalse_whenOfferIsExpired() {
        /*
        mock the dependencies required for OfferServiceImpl
        dependencies : OfferRepository, ModelMapper
        */
        OfferRepository mockRepo = Mockito.mock(OfferRepository.class);
        ModelMapper mockMapper = Mockito.mock(ModelMapper.class);

        // create a fake expired offer
        Offer sampleOffer = Offer.builder()
                .code("EXPIRED").
                validFrom(LocalDate.now().minusDays(10))
                .validTill(LocalDate.now().minusDays(1))
                .valid(true)
                .build();

        Mockito.when(mockRepo.findByCode("EXPIRED"))
                .thenReturn(Optional.of(sampleOffer));

        OfferServiceImpl offerService = new OfferServiceImpl(mockRepo, mockMapper);
        Assertions.assertFalse(offerService.isOfferValid("EXPIRED"), "Offer should be invalid when expired");

    }// isOfferTrue_shouldReturnFalse_whenOfferIsExpired() ends

    @Test
    void isOfferValid_shouldThrowException_whenOfferNotFound() {
        /*
        mock the dependencies required for OfferServiceImpl
        dependencies : OfferRepository, ModelMapper
        */
        OfferRepository mockRepo = Mockito.mock(OfferRepository.class);
        ModelMapper mockMapper = Mockito.mock(ModelMapper.class);


        Mockito.when(mockRepo.findByCode("NOTFOUND"))
                .thenReturn(Optional.empty());

        OfferServiceImpl offerService = new OfferServiceImpl(mockRepo, mockMapper);

        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> offerService.isOfferValid("NOTFOUND"));
        Assertions.assertTrue(ex.getMessage().contains("Invalid offer code"), "Offer should be invalid when offer not found");

    }// isOfferValid_shouldThrowException_whenOfferNotFound() ends

}// OfferServiceImplTest class ends
