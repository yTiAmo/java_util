package com.strong.wind.java;

import cn.hutool.core.util.EnumUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王蕾
 * @创建时间 2023/9/7 0007 13:14 周四
 * @描述
 */
public class PgsqlToJava extends CreateJavaModel {

    private final Boolean isShowFieldAnnotation;
    private final Boolean isAddDataAnnotations;
    public PgsqlToJava(Boolean isShowFieldAnnotation,Boolean isAddDataAnnotations){
        this.isShowFieldAnnotation = isShowFieldAnnotation;
        this.isAddDataAnnotations = isAddDataAnnotations;
    }
    @Override
    public Map<String, JavaModelField> getFieldAndType(String text) {
        Map<String, JavaModelField> javaModelFieldMap = new LinkedHashMap<>();
        Map<String, PgsqlToJavaEnum> pgsqlTypeToJavaTypeMap = EnumUtil.getEnumMap(PgsqlToJavaEnum.class);
        String fieldPattern = "\\\"(\\w+)\\\"\\s+(\\w+).*,";
        Pattern pattern = Pattern.compile(fieldPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String columnName = matcher.group(1);
            String pgsqlType = matcher.group(2);
            PgsqlToJavaEnum pgsqlEnum = pgsqlTypeToJavaTypeMap.get(pgsqlType.toUpperCase());
            JavaModelField javaModelField = new JavaModelField();
            javaModelField.setFieldName(columnName);
            javaModelField.setJavaType(pgsqlEnum.getJavaType());
            javaModelFieldMap.put(columnName, javaModelField);
        }
        return javaModelFieldMap;
    }

    @Override
    public List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text) {
        String commentPattern = "\"([^\"]+)\" IS '(.*?)'";
        Pattern pattern = Pattern.compile(commentPattern);
        Matcher matcher = pattern.matcher(text);
        List<JavaModelField> resultList = new LinkedList<>();
        while (matcher.find()) {
            String columnName = matcher.group(1);
            JavaModelField modelField = fieldMap.get(columnName);
            String comment = matcher.group(2);
            modelField.setComment(comment);
            resultList.add(modelField);
            fieldMap.remove(columnName);
        }
        resultList.addAll(fieldMap.values());
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
