package com.ogs.shopping.service;

import com.ogs.shopping.dto.request.RestrictedDayReqDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.RestrictedDayRespDto;

import java.time.LocalDate;
import java.util.List;

public interface RestrictedDayService {
    List<RestrictedDayRespDto> getAllRestrictedDays();
    RestrictedDayRespDto addRestrictedDay(RestrictedDayReqDto dto);
    ApiResponse removeRestrictedDay(Long id);
    boolean isRestrictedDay(LocalDate date);
}
