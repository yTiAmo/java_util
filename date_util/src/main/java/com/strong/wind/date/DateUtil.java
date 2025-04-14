package com.strong.wind.date;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;

/**
 * @author 王蕾
 * {@code @创建时间}  2023/11/28 0028 16:58 周二
 * {@code @描述}
 */
public class DateUtil {

    public static LocalDateTime str2LocalDateTime(String dateTime) {
        try {
            // 定义时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 将字符串转为 LocalDateTime
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("日期时间格式不正确，请使用格式：yyyy-MM-dd HH:mm:ss", e);
        }
    }
}

