package vn.thanhnc.tools.files.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RowExcelInfoPathDto {
    private int index;
    private String pathName;
    private String quantityFile;
    private String size;
}
