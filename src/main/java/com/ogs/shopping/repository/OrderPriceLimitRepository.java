package com.ogs.shopping.repository;

import com.ogs.shopping.entity.LimitType;
import com.ogs.shopping.entity.OrderPriceLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderPriceLimitRepository extends JpaRepository<OrderPriceLimit, Long> {
    Optional<OrderPriceLimit> findByLimitType(LimitType limitType);
}
