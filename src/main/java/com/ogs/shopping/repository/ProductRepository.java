package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}// ProductRepository interface ends
