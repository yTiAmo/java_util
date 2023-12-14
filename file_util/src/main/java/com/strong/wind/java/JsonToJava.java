package com.strong.wind.java;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 王蕾
 * {@code @创建时间}  2023/11/30 0030 13:09 周四
 * {@code @描述}
 */
public class JsonToJava extends CreateJavaModel{

    private final Boolean isShowFieldAnnotation;
    private final Boolean isAddDataAnnotations;
    public JsonToJava(Boolean isShowFieldAnnotation, Boolean isAddDataAnnotations){
        this.isShowFieldAnnotation = isShowFieldAnnotation;
        this.isAddDataAnnotations = isAddDataAnnotations;
    }
    @Override
    protected Map<String, JavaModelField> getFieldAndType(String text) {
        Map<String, JavaModelField> javaModelFieldMap = new HashMap<>();
        try {
            JSONObject jsonObject = JSON.parseObject(text);
            Set<String> fields = jsonObject.keySet();
            AtomicReference<JavaModelField> javaModelField = new AtomicReference<>();
            fields.forEach(field ->{
                javaModelField.set(new JavaModelField());
                javaModelField.get().setFieldName(field);
                javaModelField.get().setJavaType("String");
                javaModelFieldMap.put(field, javaModelField.get());
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return javaModelFieldMap;
    }

    @Override
    public List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text) {
        List<JavaModelField> resultList = new ArrayList<>();
        fieldMap.forEach((k,v)->resultList.add(v));
        return resultList;
    }

    @Override
    public Boolean isShowFieldAnnotation() {
        return isShowFieldAnnotation;
    }

    @Override
    public Boolean isAddDataAnnotations() {
        return isAddDataAnnotations;
    }


}
