package com.ogs.shopping.controller;

import com.ogs.shopping.service.ExcelReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel-sheet")
@AllArgsConstructor
public class ExcelReportController {
    private final ExcelReportService excelReportService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public void generateExcelReport(HttpServletResponse httpServletResponse) throws Exception{
        //octet-stream-> as this method is responsible to download the excel file
        httpServletResponse.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        //SEND THE FILE AS AN  ATTACHMENT
        String headerValue = "attachment; filename=products.xls";

        httpServletResponse.setHeader(headerKey, headerValue);
        excelReportService.generateExcel(httpServletResponse);
    }
}
