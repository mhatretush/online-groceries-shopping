package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.dto.request.OrderPriceLimitDto;
import com.ogs.shopping.entity.LimitType;
import com.ogs.shopping.entity.OrderPriceLimit;
import com.ogs.shopping.repository.OrderPriceLimitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderPriceLimitService {
    private OrderPriceLimitRepository repo;

    public OrderPriceLimit addLimit(OrderPriceLimitDto dto) {
        LimitType type = dto.getLimitType();
        double value = dto.getValue();

        repo.findByLimitType(type).ifPresent(l -> {
            throw new ApiException(type + " limit already exists. Use update instead.");
        });

        OrderPriceLimit limit = new OrderPriceLimit();
        limit.setLimitType(type);
        limit.setOrderPriceLimitValue(value);

        return repo.save(limit);
    }
    public OrderPriceLimit updateLimit(LimitType type, double newValue) {
        OrderPriceLimit limit = repo.findByLimitType(type)
                .orElseThrow(() -> new ApiException("Limit not found for type: " + type));
        limit.setOrderPriceLimitValue(newValue);
        return repo.save(limit);
    }

    public double getLimit(LimitType type) {
        return repo.findByLimitType(type)
                .map(OrderPriceLimit::getOrderPriceLimitValue)
                .orElseThrow(() -> new RuntimeException("Limit not found"));
    }
}
