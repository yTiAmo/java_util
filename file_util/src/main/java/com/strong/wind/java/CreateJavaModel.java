package com.strong.wind.java;

import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author 王蕾
 * @创建时间 2023/9/6 0006 17:24 周三
 * @描述
 */
public abstract class CreateJavaModel {

    public void start(String text, String outPath, String className,
                      String tableName, String pageName, String description) {

        Map<String, JavaModelField> fieldMap = getFieldAndType(text);
        List<JavaModelField> fields = getFieldAndComment(fieldMap, text);
        // 生成实体类代码
        String entityClassCode = generateEntityClass(className,tableName,pageName, fields, description);
        System.out.println(entityClassCode);
        try {
            writeToFile(entityClassCode,outPath+className+".java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 打印生成的代码
    }

    /**
     * 生成建表语句
     * @param text
     * @param tableName
     * @param description
     * @return
     */
    public String generateDDL(String text,String tableName,String description) {
        Map<String, JavaModelField> fieldMap = getFieldAndType(text);
        List<JavaModelField> fields = getFieldAndComment(fieldMap, text);
        // 生成实体类代码
        StringBuilder createTaleDDL = new StringBuilder("CREATE TABLE \"public\"." + tableName + " ( \n");
        createTaleDDL.append("  \"").append(fields.get(0).getFieldName()).append("\" varchar(100) COLLATE \"pg_catalog\".\"default\" NOT NULL,");
        for (int i = 1; i < fields.size(); i++) {
            createTaleDDL.append("\n \"").append(fields.get(i).getFieldName()).append("\" varchar(255) COLLATE \"pg_catalog\".\"default\",");
        }
        createTaleDDL.append("\n CONSTRAINT \"").append(tableName).append("_pkey\" PRIMARY KEY (\"").append(fields.get(0).getFieldName()).append("\")\n").append(")\n").append(";");

        createTaleDDL.append("\n" +
                "ALTER TABLE \"public\".\"t_sync_data_dzxx\" \n" +
                "  OWNER TO \"postgres\"; \n");

        for (int i = 1; i < fields.size(); i++) {
            createTaleDDL.append("COMMENT ON COLUMN \"public\".\""+tableName+"\".\""+fields.get(i).getFieldName()+"\" IS '"+fields.get(i).getComment()+"'; \n");
        }
        createTaleDDL.append("COMMENT ON TABLE \"public\".\""+tableName+"\" IS '"+description+"';");

        System.out.println(createTaleDDL);
        return createTaleDDL.toString();
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
     * 是否显示字段注解
     * @return
     */
    public abstract Boolean isShowFieldAnnotation();

    /**
     * 是否添加@Data注解
     * 如果添加不显示get set方法
     * @return Boolean
     */
    public abstract Boolean isAddDataAnnotations();

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
        InputStreamReader isr = new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(isr)) {
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
    public String generateEntityClass(String className, String tableName,
                                      String packageName, List<JavaModelField> fields,
                                      String description) {

        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间
        String formattedDateTime = currentDateTime.format(formatter);


        Boolean showFieldAnnotation = isShowFieldAnnotation();
        Boolean dataAnnotations = isAddDataAnnotations();
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("package ").append(packageName).append(";").append("\n\n");
        codeBuilder.append("import io.swagger.annotations.ApiModelProperty;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableField;").append("\n");
        codeBuilder.append("import com.baomidou.mybatisplus.annotation.TableName;").append("\n\n\n");
        if (Boolean.TRUE.equals(dataAnnotations)) {
            codeBuilder.append("import lombok.Data;").append("\n\n\n");
        }

        codeBuilder.append("/**\n" + " * @author 王蕾\n" + " * {@code @创建时间}  ").append(formattedDateTime).append(" \n").append(" * {@code @描述} "+description+"\n").append(" */").append("\n");
        if (Boolean.TRUE.equals(dataAnnotations)) {
            codeBuilder.append("@Data").append("\n");
        }
        codeBuilder.append("@TableName(\"").append(tableName).append("\")").append("\n");
        codeBuilder.append("public class ").append(className).append(" {\n\n");


        // 生成字段
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
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     Files.newOutputStream(Paths.get(fileName)),
                                     StandardCharsets.UTF_8))) {
            writer.write(code);
        }
    }
}
