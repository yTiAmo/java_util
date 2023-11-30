package com.strong.wind.java;

import cn.hutool.core.io.resource.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, JavaModelField> javaModelFieldMap = new HashMap<>();
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


    /**
     * 读取sql文件内容
     * @param fileName 文件名
     * @return String
     * @throws IOException IOException
     */
    public String readFile(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        String path = classPathResource.getAbsolutePath();
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
