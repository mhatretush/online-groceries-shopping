package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // find if product with given name already exists
    boolean existsByProductName(String productName);
    Optional<Product> findByProductName(String productName);
}// ProductRepository interface ends
