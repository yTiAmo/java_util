package com.strong.wind.java;

import cn.hutool.core.util.EnumUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王蕾
 * @创建时间 2023/9/7 0007 13:14 周四
 * @描述
 */
public class MysqlToJava extends CreateJavaModel{
    private final Boolean isShowFieldAnnotation;
    private final Boolean isAddDataAnnotations;
    public MysqlToJava(Boolean isShowFieldAnnotation,Boolean isAddDataAnnotations){
        this.isShowFieldAnnotation = isShowFieldAnnotation;
        this.isAddDataAnnotations = isAddDataAnnotations;
    }
    @Override
    public Map<String, JavaModelField> getFieldAndType(String text) {
        Map<String, JavaModelField> javaModelFieldMap = new HashMap<>();
        Map<String, MysqlToJavaEnum> mysqlToJavaEnumMap = EnumUtil.getEnumMap(MysqlToJavaEnum.class);
            String fieldPattern = "((\\w+)|`(\\w+)`)\\s+(\\w+).* comment\\s*('.*?')";

            Pattern pattern = Pattern.compile(fieldPattern);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String columnName = matcher.group(1);
                String columnType = matcher.group(4);
                MysqlToJavaEnum  mysqlToJavaEnum = mysqlToJavaEnumMap.get(columnType.toUpperCase());
                String comment = matcher.group(5);
                JavaModelField javaModelField = new JavaModelField();
                javaModelField.setFieldName(columnName);
                javaModelField.setJavaType(mysqlToJavaEnum.getJavaType());
                javaModelField.setComment(comment);
                javaModelFieldMap.put(columnName, javaModelField);
            }
        return javaModelFieldMap;
    }

    @Override
    public List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text) {
        List<JavaModelField> resultList = new ArrayList<>();
        fieldMap.forEach((k,v)-> resultList.add(v));
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
