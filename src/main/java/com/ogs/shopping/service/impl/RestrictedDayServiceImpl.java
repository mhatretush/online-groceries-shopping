package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.dto.request.RestrictedDayReqDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.RestrictedDayRespDto;
import com.ogs.shopping.entity.RestrictedDay;
import com.ogs.shopping.entity.RestrictedDayType;
import com.ogs.shopping.repository.RestrictedDayRepository;
import com.ogs.shopping.service.RestrictedDayService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RestrictedDayServiceImpl implements RestrictedDayService {

    private RestrictedDayRepository restrictedDayRepository;
    private ModelMapper modelMapper;

    @Override
    public List<RestrictedDayRespDto> getAllRestrictedDays() {
        return restrictedDayRepository.findAll().stream()
                .map(day -> modelMapper.map(day, RestrictedDayRespDto.class)).toList();
    }

    @Override
    public RestrictedDayRespDto addRestrictedDay(RestrictedDayReqDto dto) {
        RestrictedDay restrictedDay = modelMapper.map(dto, RestrictedDay.class);
        restrictedDay.setRestrictedDayType(RestrictedDayType.valueOf(dto.getDay().toUpperCase()));
        boolean alreadyExists = restrictedDayRepository.findAll().stream()
                .anyMatch(day -> day.equals(restrictedDay.getRestrictedDayType()));
        if(alreadyExists){
            throw new ApiException("Day  already exists");
        }

        RestrictedDay saved = restrictedDayRepository.save(restrictedDay);
        return modelMapper.map(saved, RestrictedDayRespDto.class);
    }

    @Override
    public ApiResponse removeRestrictedDay(Long id) {
        restrictedDayRepository.deleteById(id);
        return new ApiResponse("Day deleted successfully");
    }

    @Override
    public boolean isRestrictedDay(LocalDate date) {
        RestrictedDayType todayType = RestrictedDayType.valueOf(date.getDayOfWeek().name());
        return restrictedDayRepository.existsByRestrictedDayType(todayType);
    }
}