package com.strong.wind.date;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 王蕾
 * {@code @创建时间}  2023/11/28 0028 16:58 周二
 * {@code @描述}
 */
public class DateUtil {

    public static LocalDateTime str2LocalDateTime(String dataTime){
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将字符串转为 LocalTime
        return LocalDateTime.parse(dataTime, formatter);
    }
}
