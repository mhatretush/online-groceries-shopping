package com.ogs.shopping.service;

import com.ogs.shopping.entity.Product;
import com.ogs.shopping.repository.ProductRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelReportService {

    private final ProductRepository productRepository;

    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Product> products = productRepository.findAll();

        //CREATE A WORKBOOK OBJECT
        HSSFWorkbook workbook = new HSSFWorkbook();

        //CREATE A WORKBOOK SHEET
        HSSFSheet sheet = workbook.createSheet("Products Info");
        HSSFRow  row =  sheet.createRow(0);
        //CREATE A ROW AND A CELL
        row.createCell(0).setCellValue("Product ID");
        row.createCell(1).setCellValue("Product Name");
        row.createCell(2).setCellValue("Product Price");
        row.createCell(3).setCellValue("Product Quantity");
        row.createCell(4).setCellValue("Created Date");
        row.createCell(5).setCellValue("Updated Date");

        //AS THERE ARE HEADERS ON THE FIRST ROW(index=0) START FROM index=1 FOR DATAROW
        int dataRowIndex = 1;
        for(Product product:products){
            //CREATE A DATAROW AND ADD DATA
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(product.getProductId());
            dataRow.createCell(1).setCellValue(product.getProductName());
            dataRow.createCell(2).setCellValue(product.getProductPrice());
            dataRow.createCell(3).setCellValue(product.getProductQty());
            dataRow.createCell(3).setCellValue(product.getCreatedDateTime().toString());
            dataRow.createCell(3).setCellValue(product.getModifiedDateTime().toString());
            dataRowIndex++;
        }
        //CREATE A OUTPUTSTREAM AND ADD THE DATA
        ServletOutputStream outputStream =  response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
