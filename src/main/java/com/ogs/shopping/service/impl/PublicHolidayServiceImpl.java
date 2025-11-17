package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.dto.request.PublicHolidayRequestDto;
import com.ogs.shopping.dto.response.PublicHolidayResponseDto;
import com.ogs.shopping.entity.PublicHoliday;
import com.ogs.shopping.repository.PublicHolidayRepository;
import com.ogs.shopping.service.PublicHolidayService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PublicHolidayServiceImpl implements PublicHolidayService {
    private final PublicHolidayRepository publicHolidayRepository;
    private final ModelMapper modelMapper;

    @Override
    public PublicHolidayResponseDto addHoliday(PublicHolidayRequestDto holidayDto) {
        if (publicHolidayRepository.existsByDate(holidayDto.getDate())) {
        throw new ApiException("Holiday already exists");
    }
    PublicHoliday holiday = modelMapper.map(holidayDto, PublicHoliday.class);
        publicHolidayRepository.save(holiday);
        return modelMapper.map(holiday, PublicHolidayResponseDto.class);

}
    @Override
    public List<PublicHolidayResponseDto> getAllHolidays(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PublicHoliday> holidayPage = publicHolidayRepository.findAll(pageable);

        return holidayPage.getContent().stream().map(
                holiday->modelMapper.map(holiday, PublicHolidayResponseDto.class))
                .toList();

    }
//        return publicHolidayRepository.findAll().stream()
//                .map(holiday->{
//                    return modelMappe
//                    r.map(holiday,PublicHolidayResponseDto.class);
//                }).toList();

    @Override
    public ApiException deleteHoliday(Long id) {
        PublicHoliday publicHoliday = publicHolidayRepository.findById(id)
                .orElseThrow(() -> new ApiException("Holiday not found"));
        publicHolidayRepository.delete(publicHoliday);
        return new ApiException("Holiday deleted");
    }

    @Override
    public boolean isPublicHoliday(LocalDate date) {
        return publicHolidayRepository.existsByDate(date);
    }
}
