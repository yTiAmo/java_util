package com.strong.wind.java;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.map.TableMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王蕾
 * {@code @创建时间}  2023/11/30 0030 13:09 周四
 * {@code @描述}
 */
public class TextToJava extends CreateJavaModel{

    private final Boolean isShowFieldAnnotation;
    private final Boolean isAddDataAnnotations;
    public TextToJava(Boolean isShowFieldAnnotation,Boolean isAddDataAnnotations){
        this.isShowFieldAnnotation = isShowFieldAnnotation;
        this.isAddDataAnnotations = isAddDataAnnotations;
    }
    @Override
    protected Map<String, JavaModelField> getFieldAndType(String text) {
        text = text.replace("，字段描述：", ":");
        text =  text.replace("(参数名：","");
        text =  text.replace(")","");
        Map<String, JavaModelField> javaModelFieldMap = new TreeMap<>();
        String fieldPattern = "(\\w+):([\\u4e00-\\u9fa5]+)";
        Pattern pattern = Pattern.compile(fieldPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String columnName = matcher.group(1);
            String comment = matcher.group(2);
            JavaModelField javaModelField = new JavaModelField();
            javaModelField.setFieldName(columnName);
            javaModelField.setJavaType("String");
            javaModelField.setComment(comment);
            javaModelFieldMap.put(columnName, javaModelField);
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
