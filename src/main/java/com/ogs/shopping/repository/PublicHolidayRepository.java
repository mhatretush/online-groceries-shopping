package com.ogs.shopping.repository;

import com.ogs.shopping.entity.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday,Long> {
    boolean existsByDate(LocalDate date);
}
