package com.strong.wind.java;

/**
 * @author 王蕾
 * @创建时间 2023/9/6 0006 17:30 周三
 * @描述
 */
public enum PgsqlToJavaEnum {
    //pgsql to java 枚举
    DATE("date","LocalDate"),
    TIME("time","LocalTime"),
    TIMESTAMP("timestamp","LocalDateTime"),
    VARCHAR("varchar","String"),
    TEXT("String","text"),
    LOCALTIME("time","LocalTime"),
    INT2("Integer","int2"),
    INT4("Integer","int4"),
    INT8("Long","int8"),
    FLOAT4("Float","float4"),
    FLOAT8("Double","float8"),
    NUMERIC("BigDecimal","numeric"),
    BOOL("Boolean","bool"),
    ;


    private final String pgsqlType;
    private final String javaType;
    PgsqlToJavaEnum(String pgsqlType,String javaType){
        this.pgsqlType = pgsqlType;
        this.javaType = javaType;
    }
    public String getPgsqlType() {
        return pgsqlType;
    }

    public String getJavaType() {
        return javaType;
    }

}
