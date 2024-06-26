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
        CreateJavaModel pgsqlToJava = new PgsqlToJava(true,true);
        CreateJavaModel mysqlToJava = new MysqlToJava(true,true);
        CreateJavaModel textToJava = new TextToJava(true,true);
        CreateJavaModel jsonToJava = new JsonToJava(true,true);
        String input = textToJava.readFile("/templates/filed.txt");
        textToJava.start(input,"F:\\daoshu\\project\\java\\ypcj\\mti-community\\mti-community-api\\src\\main\\java\\com\\mti\\community\\model\\sync\\ypsqy\\",
                "LabelInformationIsDeliveredToRes",
                "t_sync_data_bqxxxf",
                "com.mti.community.model.sync.ypsqy",
                "杨浦区人员信息-房屋信息-人房关系");

//        textToJava.generateDDL(input, "t_sync_data_rfgx","杨浦区人员信息-房屋信息-人房关系（殷行融合）");
    }
}
