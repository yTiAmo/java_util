package com.strong.wind;


import com.strong.wind.java.CreateJavaModel;
import com.strong.wind.java.MysqlToJava;
import com.strong.wind.java.PgsqlToJava;
import com.strong.wind.java.TextToJava;

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
        CreateJavaModel textToJava = new TextToJava(false,true);
        String input = textToJava.readFile("/templates/filed.txt");
        textToJava.start(input,"D:\\","DictVO",
                "z","com.mti.data.tz.vo");
    }
}
