package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer,Long> {
   Optional<Offer> findByCode(String code);
}
