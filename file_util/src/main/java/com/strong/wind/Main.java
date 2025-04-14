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
        CreateJavaModel pgsqlToJava = new PgsqlToJava(true,false);
        CreateJavaModel mysqlToJava = new MysqlToJava(true,true);
        CreateJavaModel textToJava = new TextToJava(true,true);
        CreateJavaModel jsonToJava = new JsonToJava(true,true);
        String input = pgsqlToJava.readFile("/templates/pgsql.txt");
        pgsqlToJava.start(input,"F:\\daoshu\\project\\java\\舟山后台配置管理系统\\mti-sysconfig\\src\\main\\java\\com\\daoshu\\modules\\screen\\entity\\",
                "TThirdBkYj",
                "t_third_bk_yj",
                "com.daoshu.modules.screen.entity",
                "预警人员");

//        textToJava.generateDDL(input, "t_sync_data_rfgx","杨浦区人员信息-房屋信息-人房关系（殷行融合）");
    }
}
