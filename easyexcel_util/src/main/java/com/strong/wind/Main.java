package com.strong.wind;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.strong.wind.easyexcel.FillData;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<FillData> fillDatas = new ArrayList<>();
        List<FillData> fillDatas2 = new ArrayList<>();
        FillData fillData = new FillData("王雷",123.0,new Date());
        FillData fillData2 = new FillData("王雷1",1232.0,new Date());
        FillData fillData3 = new FillData("王雷2",12132.0,new Date());
        fillDatas.add(fillData);
        fillDatas.add(fillData2);
        fillDatas2.add(fillData3);
        String templateFileName = "F:\\private\\java\\java_util\\easyexcel_util\\src\\main\\resources\\111.xlsx";
        String fileName = "F:\\private\\java\\java_util\\easyexcel_util\\src\\main\\resources\\222.xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
//            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
//            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
//            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
//            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
//            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
//            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data",fillDatas), writeSheet);
            excelWriter.fill(new FillWrapper("data2", fillDatas2), writeSheet);
//            excelWriter.fill(fillData,writeSheet);
//            Map<String, Object> map = new HashMap<>();
//            //map.put("date", "2019年10月9日13:28:28");
//            map.put("date", new Date());

//            excelWriter.fill(map, writeSheet);
        }
    }
}