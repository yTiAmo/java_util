package com.strong.wind.poiExcel;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wanglei
 * @date 2022年06月07日 11:18
 */
public final class TimeUtils {

    private TimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    private static final String DAY_START_TIME = "00:00:00";
    private static final String DAY_END_TIME = "23:59:59";
    private static final String YYYY_MM = "yyyy-MM";


    public static LocalDate[] latest30Days(LocalDate localDate) {
        int len = 30;
        LocalDate[] localDates = new LocalDate[len];

        for (int i = 0; i < len; i++) {

            localDates[i] = localDate.minusDays(i);
        }

        return localDates;
    }


    public static LocalDateTime atEndOfDay(Long timestamp) {
        LocalDate localDate = convert2LocalDate(timestamp);
        return atEndOfDay(localDate);
    }


    public static LocalDateTime atEndOfDay(LocalDate localDate) {
        String timeString = formatLocalDate(localDate) + " " + DAY_END_TIME;

        return parseDateTime(timeString);
    }

    /**
     * 获取 date 日期 的上一周的开始结束日期，即使给定的日期是周一 也会 计算上一周的日期
     *
     * @param date 基准日期点
     * @return [0]:上一周的开始日期；[1] 上一周的结束日期
     */
    public static LocalDate[] getLastWeek(LocalDate date) {

        LocalDate[] weeks = new LocalDate[2];

        LocalDate localDate = date.minusWeeks(1);

        weeks[0] = localDate.with(DayOfWeek.MONDAY);
        weeks[1] = localDate.with(DayOfWeek.SUNDAY);

        return weeks;
    }

    /**
     * 获取 date 日期所在的周 的开始日期和结束日期
     *
     * @param date 基准日期
     * @return [0]:周的开始日期；[1] 周的结束日期
     */
    public static LocalDate[] getThisWeek(LocalDate date) {

        LocalDate[] weeks = new LocalDate[2];

        weeks[0] = date.with(DayOfWeek.MONDAY);
        weeks[1] = date.with(DayOfWeek.SUNDAY);

        return weeks;
    }

    /**
     * 获取 基准日期date 的上一个月的开始日期和结束日期
     *
     * @param date 基准日期点
     * @return [0]:上一个月的开始日期；[1] 上一个月的结束日期
     */
    public static LocalDate[] getLastMonth(LocalDate date) {

        LocalDate[] month = new LocalDate[2];
        LocalDate localDate = date.minusMonths(1);

        //startOfMonth
        month[0] = localDate.withDayOfMonth(1);

        //上一个月的天数

        int lengthOfMonth = localDate.lengthOfMonth();
        //endOfMonth
        month[1] = localDate.withDayOfMonth(lengthOfMonth);

        return month;
    }

    /**
     * 获取 基准日期date 的【所在】月的开始日期和结束日期
     *
     * @param date 基准日期
     * @return [0]:月的开始日期；[1] 月的结束日期
     */
    public static LocalDate[] getThisMonth(LocalDate date) {

        LocalDate[] month = new LocalDate[2];

        //startOfMonth
        month[0] = date.withDayOfMonth(1);

        //上一个月的天数

        int lengthOfMonth = date.lengthOfMonth();
        //endOfMonth
        month[1] = date.withDayOfMonth(lengthOfMonth);

        return month;
    }

    /**
     * 获取某一个时间得方法
     */
    public static LocalDate getYesterday() {

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        return now.minusDays(1);
    }

    public static String localDateNow() {
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        return formatLocalDate(now);
    }

    public static String localTimeNow() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return now.format(dateTimeFormatter);
    }

    public static LocalDateTime todayStartTime() {
        return todayTime(DAY_START_TIME);
    }

    public static LocalDateTime todayEndTime() {
        return todayTime(DAY_END_TIME);
    }


    public static String getTime(LocalDateTime time) {
        return formatDateTime2String(time, TIME_FORMAT);
    }

    public static String getDate(LocalDateTime dateTime) {
        return formatDateTime2String(dateTime, DATE_FORMAT);
    }


    /**
     * 时间转换的方法
     */
    public static Date convert2Date(LocalDate localDate) {

        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date convert2Date(LocalDateTime dateTime) {
        Instant instant = convert2Instant(dateTime);
        return Date.from(instant);
    }

    public static LocalDate convert2LocalDate(Date date) {
        return convert2LocalDateTime(date).toLocalDate();
    }


    public static LocalDateTime convert2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

    }

    public static LocalDateTime convert2LocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime convert2LocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return convert2LocalDateTime(instant);
    }

    public static LocalDate convert2LocalDate(Long timestamp) {
        LocalDateTime time = convert2LocalDateTime(timestamp);
        return time.toLocalDate();
    }

    public static Long convert2Timestamp(LocalDateTime time) {
        return convert2Instant(time).toEpochMilli();
    }

    public static Instant convert2Instant(LocalDateTime time) {
        return time.toInstant(getOffset());
    }


    /**
     * 解析时间
     */

    public static LocalDateTime parseDateTime(String dateTime) {
        return parseDateTime(dateTime, DATETIME_FORMAT);
    }

    public static LocalDateTime parseDateTime(String dateTime, String pattern) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.from(timeFormatter.parse(dateTime));
    }

    public static LocalDate parseLocalDate(String date) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, pattern);
    }

    /**
     * 格式化时间 方法
     */

    public static String formatLocalDate(LocalDate now) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return now.format(dateTimeFormatter);
    }


    public static String formatLocalDate(LocalDateTime now) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return now.format(dateTimeFormatter);
    }


    public static String formatDateTime2String(LocalDateTime dateTime) {
        return formatDateTime2String(dateTime, DATETIME_FORMAT);
    }


    public static String formatDateTime2String(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(timeFormatter);
    }


    public static String formatYearMonth2String(LocalDate date) {
        return formatDate2String(date, YYYY_MM);
    }

    public static String formatDate2String(LocalDate date, String pattern) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(timeFormatter);
    }

    /**
     * 计算 method
     */

    public static int diff(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start date must be less than end date");
        }
        return (int) (end.toEpochDay() - start.toEpochDay());
    }


    public static Long[] checkStartTimeAndEndTimeParams(Long startTime, Long endTime) {
        if (startTime != null && startTime != 0) {

            long now = Instant.now().toEpochMilli();

            if (now < startTime) {
                throw new IllegalArgumentException("开始时间不能大于现在的时间");
            }

            if (endTime == null) {
                endTime = now;
            } else {
                if (endTime < startTime) {
                    throw new IllegalArgumentException("开始时间必须小于结束时间");
                }
            }
        }

        return new Long[]{startTime, endTime};
    }

    /**
     * 私有方法
     */

    private static LocalDateTime todayTime(String time) {

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        String dateTime = now + " " + time;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

        return LocalDateTime.from(timeFormatter.parse(dateTime));
    }

    private static ZoneOffset getOffset() {
        return ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());
    }

}

