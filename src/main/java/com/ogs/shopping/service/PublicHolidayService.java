package com.ogs.shopping.service;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.dto.request.PublicHolidayRequestDto;
import com.ogs.shopping.dto.response.PublicHolidayResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface PublicHolidayService {
    PublicHolidayResponseDto addHoliday(PublicHolidayRequestDto holidayDto);
    List<PublicHolidayResponseDto> getAllHolidays();
    ApiException deleteHoliday(Long id);
    boolean isPublicHoliday(LocalDate date);
}