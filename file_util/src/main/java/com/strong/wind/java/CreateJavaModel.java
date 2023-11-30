package com.strong.wind.java;

import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author ����
 * @����ʱ�� 2023/9/6 0006 17:24 ����
 * @����
 */
public abstract class CreateJavaModel {

    public void start(String text, String outPath, String className,
                      String tableName, String pageName) {

        Map<String, JavaModelField> fieldMap = getFieldAndType(text);
        List<JavaModelField> fields = getFieldAndComment(fieldMap, text);
        // ����ʵ�������
        String entityClassCode = generateEntityClass(className,tableName,pageName, fields);
        System.out.println(entityClassCode);
        try {
            writeToFile(entityClassCode,outPath+className+".java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // ��ӡ���ɵĴ���

    }


    /**
     * ������ʽ��ȡ�ֶ������ֶ�����
     * @param text ddl�������
     * @return Map<String, JavaModelField>
     */
    protected abstract Map<String, JavaModelField> getFieldAndType(String text);

    /**
     *
     * @param fieldMap �ֶμ���
     * @param text text ddl�������
     * @return List<JavaModelField>
     */
    public abstract List<JavaModelField> getFieldAndComment(Map<String, JavaModelField> fieldMap, String text);

    /**
     * �Ƿ���ʾ�ֶ�ע��
     * @return
     */
    public abstract Boolean isShowFieldAnnotation();

    /**
     * �Ƿ����@Dataע��
     * �����Ӳ���ʾget set����
     * @return Boolean
     */
    public abstract Boolean isAddDataAnnotations();

    /**
     * ��ȡsql�ļ�����
     * @param fileName �ļ���
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
     * ����java�ļ�
     * @param className java����
     * @param tableName ����
     * @param packageName ����
     * @param fields �ֶ��б�
     * @return String
     */
    public String generateEntityClass(String className, String tableName,
                                      String packageName, List<JavaModelField> fields) {
        Boolean showFieldAnnotation = isShowFieldAnnotation();
        Boolean dataAnnotations = isAddDataAnnotations();
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("package ").append(packageName).append(";").append("\n\n");
        codeBuilder.append("import io.swagger.annotations.ApiModelProperty;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableField;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableName;").append("\n\n\n");
        if (Boolean.TRUE.equals(dataAnnotations)) {
            codeBuilder.append("import lombok.Data;").append("\n\n\n");
            codeBuilder.append("@Data").append("\n");
        }
        codeBuilder.append("@TableName(\"").append(tableName).append("\")").append("\n");
        codeBuilder.append("public class ").append(className).append(" {\n\n");


        // �����ֶ�
        for (JavaModelField field : fields) {
            if (Boolean.TRUE.equals(showFieldAnnotation)) {
                codeBuilder.append("    @TableField(\"").append(field.getFieldName()).append("\") ").append("\n");
            }
            codeBuilder.append("    @ApiModelProperty(value = \"").append(field.getComment()).append("\")").append("\n");
            if (field.getFieldName().contains("_")) {
                field.setFieldName(convertToCamelCase(field.getFieldName()));
            }
            codeBuilder.append("    private ").append(field.getJavaType()).append(" ").append(field.getFieldName()).append(";\n\n");
        }

        if (Boolean.TRUE.equals(!dataAnnotations)) {
            // ���� getters �� setters
            for (JavaModelField field : fields) {
                String capitalizedFieldName = capitalize(field.getFieldName());
                codeBuilder.append("    public ").append(field.getJavaType()).append(" get").append(capitalizedFieldName).append("() {\n");
                codeBuilder.append("        return ").append(field.getFieldName()).append(";\n");
                codeBuilder.append("    }\n\n");
                codeBuilder.append("    public void set").append(capitalizedFieldName).append("(").append(field.getJavaType()).append(" ").append(field.getFieldName()).append(") {\n");
                codeBuilder.append("        this.").append(field.getFieldName()).append(" = ").append(field.getFieldName()).append(";\n");
                codeBuilder.append("    }\n\n");
            }
        }
        codeBuilder.append("}");

        return codeBuilder.toString();
    }

    /**
     * ������ĸ��д
     * @param s �ֶ���
     * @return String
     */
    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * �����ݿ��ֶ�ת��java�շ�����
     * @param dbFieldName dbFieldName
     * @return
     */
    public static String convertToCamelCase(String dbFieldName) {
        StringBuilder camelCase = new StringBuilder();

        // ����Ƿ���Ҫ����һ���ַ�ת��Ϊ��д
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
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     Files.newOutputStream(Paths.get(fileName)),
                                     StandardCharsets.UTF_8))) {
            writer.write(code);
        }
    }
}
