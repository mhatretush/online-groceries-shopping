package com.ogs.shopping.controller;

import com.ogs.shopping.dto.request.RestrictedDayReqDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.RestrictedDayRespDto;
import com.ogs.shopping.service.RestrictedDayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restricted-days")
@AllArgsConstructor
public class RestrictedDayController {
    private RestrictedDayService restrictedDayService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRestrictedDays() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restrictedDayService.getAllRestrictedDays());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRestrictedDay(@RequestBody RestrictedDayReqDto dto) {
        RestrictedDayRespDto savedDay = restrictedDayService.addRestrictedDay(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDay);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRestrictedDay(@PathVariable Long id) {
        ApiResponse response = restrictedDayService.removeRestrictedDay(id);
        return ResponseEntity.ok(response);
    }
}
