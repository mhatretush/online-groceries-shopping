package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Offer;
import com.ogs.shopping.entity.OfferClaim;
import com.ogs.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferClaimRepository extends JpaRepository<OfferClaim,Long> {
    boolean existByUserIdAndOfferId(User userId, Offer offerId);
}
