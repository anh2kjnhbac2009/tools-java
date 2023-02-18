package vn.thanhnc.tools.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.thanhnc.tools.files.dto.RowExcelInfoPathDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    public static void createFileExcel(List<RowExcelInfoPathDto> lstData) throws IOException {
        // Create setting
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 13);

        // Create content
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        int rowNum = 0;
        for(RowExcelInfoPathDto item : lstData){
            Row row = sheet.createRow(rowNum++);
            item.setIndex(rowNum);
            createList(item, row);
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private static void createList(RowExcelInfoPathDto item, Row row){
        Cell cell = row.createCell(0);
        cell.setCellValue(item.getIndex());

        cell = row.createCell(1);
        cell.setCellValue(item.getPathName());

        cell = row.createCell(2);
        cell.setCellValue(item.getQuantityFile());

        cell = row.createCell(3);
        cell.setCellValue(item.getSize());
    }
}
