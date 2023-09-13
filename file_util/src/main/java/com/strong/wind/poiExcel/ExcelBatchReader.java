package com.strong.wind.poiExcel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.strong.wind.annotation.ExcelHeader;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ExcelBatchReader<T> {


    /**
     * 输出流
     */
    private final InputStream inputStream;
    private final Class<T> clz;

    /**
     * 表头
     */
    private final List<String> headers = new ArrayList<>();

    /**
     * 实体字段名
     */
    private final List<Field> fields = new ArrayList<>();


    /**
     * 字典注解
     */
    private final Map<Field, ExcelHeader> excelAnnotationMap = new HashMap<>();


    /**
     * 表头对应的 字段
     */
    private final Map<String, Field> HEADER_FIELD = new HashMap<>();


    /**
     * ExcelFastBatchWriter 构造器
     *
     * @param inputStream 输入流
     */
    public ExcelBatchReader(InputStream inputStream, Class<T> clz) {
        this.inputStream = inputStream;
        this.clz = clz;
        init(clz);
    }


    public void init(Class<?> clz){
        try {
            Class<?> tempClass = clz;
            List<Field> tempFields = new ArrayList<>();
            while (tempClass != null) {
                Collections.addAll(tempFields, tempClass.getDeclaredFields());
                tempClass = tempClass.getSuperclass();
            }
            for (Field field : tempFields) {
                ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
                if (excelHeader!= null) {
                    headers.addAll(Arrays.asList(excelHeader.value()));
                    fields.add(field);
                    excelAnnotationMap.put(field,excelHeader);
                    HEADER_FIELD.put((excelHeader.value())[0],field);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<T> importExcel2(){
        return importExcel(clz);
    }

    public List<T> importExcel() {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> mapList = reader.readAll();
        List<JSONObject> list = new ArrayList<>();
        if (!mapList.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            mapList.forEach(s-> s.forEach((k, v)->{
                Field field = HEADER_FIELD.get(k);
                jsonObject.put(field.getName(),v);
                list.add(jsonObject);
            }));
        }

       return JSON.parseArray(JSON.toJSONString(list),clz);
    }

    private List<T> importExcel(Class<T> clz) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        return reader.readAll(clz);
    }
}
