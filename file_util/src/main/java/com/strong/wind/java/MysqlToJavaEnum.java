package com.strong.wind.java;

/**
 * @author 王蕾
 * @创建时间 2023/9/19 0019 10:03 周二
 * @描述
 */
public enum MysqlToJavaEnum {

    TINYINT("TINYINT","Byte"),
    SHORT("short","Short"),
    SMALLINT("smallint","Integer"),
    INT("int","Long"),
    MEDIUMINT("mediumint","Long"),
    BIGINT("bigint","Long"),

    FLOAT("float","Float"),
    DOUBLE("double","Double"),

    DECIMAL("decimal","BigDecimal"),
    CHAR("char","String"),
    VARCHAR("varchar","String"),
    TINYTEXT("tinytext","String"),
    TEXT("text","String"),
    MEDIUMTEXT("mediumtext","String"),
    LONGTEXT("longtext","String"),
    DATE("date","Date"),
    DATETIME("datetime","Timestamp"),
    TIMESTAMP("timestamp","Timestamp"),
    TIME("time","Time"),
    BLOB("blob","byte[]"),
    BIT("bit","Boolean"),
    ;

    private final String mysqlType;
    private final String javaType;


    MysqlToJavaEnum(String mysqlType, String javaType){
        this.mysqlType = mysqlType;
        this.javaType = javaType;
    }

    public String getMysqlType() {
        return mysqlType;
    }

    public String getJavaType() {
        return javaType;
    }
}
