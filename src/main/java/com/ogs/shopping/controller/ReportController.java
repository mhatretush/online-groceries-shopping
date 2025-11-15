package com.ogs.shopping.controller;

import com.ogs.shopping.service.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{reportFormat}")
    @PreAuthorize("hasRole('ADMIN')")
    public String generateReport(@PathVariable String reportFormat) throws JRException, FileNotFoundException {
        return reportService.exportReport(reportFormat);
    }

}
