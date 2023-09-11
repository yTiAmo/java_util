package com.strong.wind.excel;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王蕾
 * @创建时间 2023/9/8 0008 10:20 周五
 * @描述
 */
public class ExcelUtils {

    @SneakyThrows
    private static List<String> getExcelData(String filePath){
        List<String> list = new ArrayList<>();
        //获取jar或者项目所在目录下的同级111.xlsx文件
        String path = FileUtil.file(".", filePath).getCanonicalPath();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            ImportExcelVO excelVO;
            int o =0;
            for (Row row : workbook.getSheetAt(i)) {
                if (0!=o){
                    excelVO = new ImportExcelVO();
                    String firstLevelDirectory = row.getCell(0).getStringCellValue();
                    excelVO.setZjhm(firstLevelDirectory);
                    list.add(firstLevelDirectory);
                }
                o++;
            }
        }
        return list;
    }

    public static void main(String[] args) {
//        List<String> excelData = getExcelData("11111.xlsx");  310104201107094030','31010720141019341X
        List<String> excelData2 = getExcelData("2222.xlsx");
        List<String> excelData3 = getExcelData("3333.xlsx");
        String differentElements = excelData3.stream()
                .filter(element -> !excelData2.contains(element))
                .collect(Collectors.joining("','"));

        // 输出不同的数据
        System.out.println("不同的数据："+differentElements);

    }

}
