package com.ogs.shopping.service;

import com.ogs.shopping.entity.Product;
import com.ogs.shopping.repository.ProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ProductRepository productRepository;
    String path = "D:\\training\\Jasper";
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Product> products = productRepository.findAll();
        //LOAD FILE AND COMPILE IT
        //LOAD
        File file = ResourceUtils.getFile("classpath:product.jrxml");
        //COMPILE
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createdBy","admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);

        String fileName = "product_report." + reportFormat.toLowerCase();
        String fullPath = path + "\\" + fileName;

        //CHECK FILE FORMAT
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, fullPath);
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullPath);
        }
        return "Report generated"+path;
    }
}
