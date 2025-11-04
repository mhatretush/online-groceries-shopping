package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.PublicHolidayRequestDto;
import com.ogs.shopping.repository.PublicHolidayRepository;
import com.ogs.shopping.service.PublicHolidayService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidays")
@AllArgsConstructor
@Validated
public class PublicHolidayController {

    private final PublicHolidayService publicHolidayService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addHoliday(@Valid @RequestBody PublicHolidayRequestDto holidayDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publicHolidayService.addHoliday(holidayDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllHolidays(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(publicHolidayService.getAllHolidays(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHoliday(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(publicHolidayService.deleteHoliday(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkHoliday(@RequestParam("date") String date) {
        boolean isHoliday = publicHolidayService.isPublicHoliday(java.time.LocalDate.parse(date));
        return ResponseEntity.ok(isHoliday);
    }
}
