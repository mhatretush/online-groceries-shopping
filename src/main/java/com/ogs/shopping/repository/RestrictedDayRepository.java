package com.ogs.shopping.repository;

import com.ogs.shopping.entity.RestrictedDay;
import com.ogs.shopping.entity.RestrictedDayType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestrictedDayRepository extends JpaRepository<RestrictedDay, Long> {
    boolean existsByRestrictedDayType(RestrictedDayType restrictedDayType);
}