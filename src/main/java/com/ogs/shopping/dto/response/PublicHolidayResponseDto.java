package com.ogs.shopping.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PublicHolidayResponseDto {
    private LocalDate date;
    private String name;
}
