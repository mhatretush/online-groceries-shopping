package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer,Long> {
<<<<<<< HEAD

    Optional<Offer> findByCode(String code);
=======
   Optional<Offer> findByCode(String code);
>>>>>>> c3c3b29e88886342670a8290dfcb97f4c544c165
}
