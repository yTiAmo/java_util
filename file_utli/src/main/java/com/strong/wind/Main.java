package com.strong.wind;


import com.strong.wind.file_util.java.CreateJavaModel;
import com.strong.wind.file_util.java.MysqlToJava;
import com.strong.wind.file_util.java.PgsqlToJava;

import java.io.IOException;

/**
 * @author 王蕾
 * @创建时间 ${DATE} $TIME $DAY_NAME_SHORT
 * @描述
 */
public class Main {
    public static void main(String[] args) throws IOException {
        CreateJavaModel pgsqlToJava = new PgsqlToJava();
        CreateJavaModel mysqlToJava = new MysqlToJava();
        String input = pgsqlToJava.readFile("/templates/pgsql.txt");
        pgsqlToJava.start(input,"D:\\","StgQdsjzxRyjbxxYhDf",
                "stg_qdsjzx_ryjbxx_yh_df","com.mti.community.model.person");
    }
}
