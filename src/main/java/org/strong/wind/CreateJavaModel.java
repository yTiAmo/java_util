package org.strong.wind;

import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;
import java.util.*;

/**
 * @author 王蕾
 * @创建时间 2023/9/6 0006 17:24 周三
 * @描述
 */
public abstract class CreateJavaModel {

    public void start(String text, String outPath, String className, String tableName, String pageName) {
        Map<String, JavaModelField> fieldMap = getFieldAndType(text);
        List<JavaModelField> fields = getFieldAndComment(fieldMap, text);
        // 生成实体类代码
        String entityClassCode = generateEntityClass(className,tableName,pageName, fields);
        try {
            writeToFile(entityClassCode,outPath+className+".java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 打印生成的代码
        System.out.println(entityClassCode);
    }


    /**
     * 正则表达式获取字段名和字段类型
     * @param text ddl创建语句
     * @return Map<String, JavaModelField>
     */
    protected abstract Map<String, JavaModelField> getFieldAndType(String text);

    /**
     *
     * @param fieldMap 字段集合
     * @param text text ddl创建语句
     * @return List<JavaModelField>
     */
    public abstract List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text);

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

    /**
     * 生成java文件
     * @param className java类名
     * @param tableName 表名
     * @param packageName 包名
     * @param fields 字段列表
     * @return String
     */
    public String generateEntityClass(String className, String tableName,String packageName, List<JavaModelField> fields) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("package ").append(packageName).append(";").append("\n\n");
        codeBuilder.append("import io.swagger.annotations.ApiModelProperty;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableField;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableName;").append("\n\n\n");
        codeBuilder.append("@TableName(\"").append(tableName).append("\")").append("\n");
        codeBuilder.append("public class ").append(className).append(" {\n\n");


        // 生成字段
        for (JavaModelField field : fields) {
            codeBuilder.append("    @TableField(\"").append(field.getFieldName()).append("\") ").append("\n");
            codeBuilder.append("    @ApiModelProperty(value = \"").append(field.getComment()).append("\")").append("\n");
            if (field.getFieldName().contains("_")) {
                field.setFieldName(convertToCamelCase(field.getFieldName()));
            }
            codeBuilder.append("    private ").append(field.getJavaType()).append(" ").append(field.getFieldName()).append(";\n\n");
        }

        // 生成 getters 和 setters
        for (JavaModelField field : fields) {
            String capitalizedFieldName = capitalize(field.getFieldName());
            codeBuilder.append("    public ").append(field.getJavaType()).append(" get").append(capitalizedFieldName).append("() {\n");
            codeBuilder.append("        return ").append(field.getFieldName()).append(";\n");
            codeBuilder.append("    }\n\n");
            codeBuilder.append("    public void set").append(capitalizedFieldName).append("(").append(field.getJavaType()).append(" ").append(field.getFieldName()).append(") {\n");
            codeBuilder.append("        this.").append(field.getFieldName()).append(" = ").append(field.getFieldName()).append(";\n");
            codeBuilder.append("    }\n\n");
        }

        codeBuilder.append("}");

        return codeBuilder.toString();
    }

    /**
     * 将首字母大写
     * @param s 字段名
     * @return String
     */
    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 将数据库字段转成java驼峰命名
     * @param dbFieldName dbFieldName
     * @return
     */
    public static String convertToCamelCase(String dbFieldName) {
        StringBuilder camelCase = new StringBuilder();

        // 标记是否需要将下一个字符转换为大写
        boolean nextUpperCase = false;

        for (int i = 0; i < dbFieldName.length(); i++) {
            char currentChar = dbFieldName.charAt(i);

            if (currentChar == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    camelCase.append(Character.toLowerCase(currentChar));
                }
            }
        }

        return camelCase.toString();
    }

    public static void writeToFile(String code, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(code);
            writer.close();
        }
    }
}
