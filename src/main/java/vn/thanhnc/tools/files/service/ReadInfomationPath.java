package vn.thanhnc.tools.files.service;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import vn.thanhnc.tools.files.dto.RowExcelInfoPathDto;
import vn.thanhnc.tools.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadInfomationPath {
    private static final String PATH_FOLDER = "D:\\Cong_Viec\\ETC\\CORE\\10.WIP\\50.PROGRAM\\KTNB\\ktnb-ui";
    private static final String PATH_FOLDER_REPLACE = "D:\\Cong_Viec\\ETC\\CORE\\10.WIP\\50.PROGRAM\\KTNB\\";
    private static final BigDecimal BIG_DECIMAL_1024 = BigDecimal.valueOf(1024);

    @SneakyThrows
    public static void main(String[] args) {
        readPathFolderExportExcel();
    }

    public static void readPathFolderExportExcel() throws IOException {
        List<RowExcelInfoPathDto> lstData = new ArrayList();
        readPath(PATH_FOLDER, lstData);
        ExcelUtils.createFileExcel(lstData);
    }

    private static void readPath(String path, List<RowExcelInfoPathDto> lstData) {
        RowExcelInfoPathDto item = new RowExcelInfoPathDto();
        item.setPathName(path.replace(PATH_FOLDER_REPLACE, ""));
        Path dir = Paths.get(path);
        int quantity = 0;
        BigDecimal size = BigDecimal.ZERO;

        // Process if path is file
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                File file = p.toFile();
                if(file.isFile()) {
                    quantity = quantity + 1;
                    size = size.add(BigDecimal.valueOf(file.length()));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        item.setQuantityFile(quantity + " File");
        if(size == BigDecimal.ZERO || size.compareTo(BIG_DECIMAL_1024) == -1){
            item.setSize(size + " Byte");
        }else if(size.compareTo(BigDecimal.ONE) != 1){
            item.setSize("1 Byte");
        }else if(size.compareTo(BIG_DECIMAL_1024) != -1 && size.compareTo(BIG_DECIMAL_1024.multiply(BIG_DECIMAL_1024)) == -1){
            item.setSize(size.divide(BIG_DECIMAL_1024, RoundingMode.UP) + " KB");
        }else{
            item.setSize(size.divide(BIG_DECIMAL_1024.multiply(BIG_DECIMAL_1024), RoundingMode.UP) + " MB");
        }
        lstData.add(item);

        // Process if path is folder
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                File file = p.toFile();
                if(file.isDirectory()) {
                    readPath(file.getAbsolutePath(), lstData);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
