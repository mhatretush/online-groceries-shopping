package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Offer;
import com.ogs.shopping.entity.OfferClaim;
import com.ogs.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferClaimRepository extends JpaRepository<OfferClaim,Long> {
    boolean existsByUserAndOffer(User user, Offer offer);


    Optional<OfferClaim> findByUserAndOffer(User user, Offer offer);


    List<OfferClaim> findByUser_UserId(Long userId);


    List<OfferClaim> findByOffer_OfferId(Long offerId);
}
