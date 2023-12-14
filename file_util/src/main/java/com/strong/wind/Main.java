package com.strong.wind;


import com.strong.wind.java.*;

import java.io.IOException;

/**
 * @author 王蕾
 * @创建时间 ${DATE} $TIME $DAY_NAME_SHORT
 * @描述
 */
public class Main {
    public static void main(String[] args) throws IOException {
        CreateJavaModel pgsqlToJava = new PgsqlToJava(false,true);
        CreateJavaModel mysqlToJava = new MysqlToJava(true,true);
        CreateJavaModel textToJava = new TextToJava(true,true);
        CreateJavaModel jsonToJava = new JsonToJava(true,true);
        String input = mysqlToJava.readFile("/templates/mysql.txt");
        mysqlToJava.start(input,"F:\\daoshu\\project\\java\\ypcj\\mti-community\\mti-community-api\\src\\main\\java\\com\\mti\\community\\model\\data\\","",
                "","com.mti.community.model.data");
    }
}
