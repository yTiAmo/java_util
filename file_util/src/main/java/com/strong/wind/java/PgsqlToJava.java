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
public class PgsqlToJava extends CreateJavaModel {

    @Override
    public Map<String, JavaModelField> getFieldAndType(String text) {
        Map<String, JavaModelField> javaModelFieldMap = new HashMap<>();
        Map<String, PgsqlToJavaEnum> pgsqlTypeToJavaTypeMap = EnumUtil.getEnumMap(PgsqlToJavaEnum.class);
        for (PgsqlToJavaEnum pgsqlEnum : pgsqlTypeToJavaTypeMap.values()) {
            String fieldPattern = "\"(\\w+)\" " + pgsqlEnum.getPgsqlType() + "\\(\\d+\\)";
            Pattern pattern = Pattern.compile(fieldPattern);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String columnName = matcher.group(1);
                JavaModelField javaModelField = new JavaModelField();
                javaModelField.setFieldName(columnName);
                javaModelField.setJavaType(pgsqlEnum.getJavaType());
                javaModelFieldMap.put(columnName, javaModelField);
            }
        }
        return javaModelFieldMap;
    }

    @Override
    public List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text) {
        String commentPattern = "\"([^\"]+)\" IS '([^\"]+)'";
        Pattern pattern = Pattern.compile(commentPattern);
        Matcher matcher = pattern.matcher(text);
        List<JavaModelField> resultList = new ArrayList<>();

        while (matcher.find()) {
            String columnName = matcher.group(1);
            if (fieldMap.containsKey(columnName)) {
                JavaModelField modelField = fieldMap.get(columnName);
                String comment = matcher.group(2);
                modelField.setComment(comment);
                resultList.add(modelField);
            }
        }
        return resultList;
    }

    @Override
    public Boolean isShowFieldAnnotation() {
        return true;
    }

    @Override
    public Boolean isAddDataAnnotations() {
        return false;
    }

}
