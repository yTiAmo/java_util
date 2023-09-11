package com.strong.wind.poiExcel;


import com.strong.wind.annotation.ExcelHeader;

/**
 * 处理 excel 单元格的值
 *
 * @author peter
 * create: 2020-01-08 09:55
 **/
public interface ExcelCellValueHandler {



    /**
     * 处理注解值
     * @param value  值
     * @param annotation 注解
     * @return String
     */
    String handle(Object value, ExcelHeader annotation);

}

