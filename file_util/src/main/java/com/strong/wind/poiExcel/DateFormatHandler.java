package com.strong.wind.poiExcel;


import com.strong.wind.annotation.ExcelHeader;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


/**
 * @author wanglei
 * @date 2022年06月07日 11:17
 */
public class DateFormatHandler implements ExcelCellValueHandler {

    @Override
    public String handle(Object value, ExcelHeader annotation) {
        if (Objects.isNull(value)) return "";
        if (value instanceof LocalDateTime) {
            return TimeUtils.formatDateTime2String((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            return TimeUtils.formatLocalDate((LocalDate) value);
        } else if (value instanceof Instant) {
            LocalDateTime time = TimeUtils.convert2LocalDateTime(((Instant) value));
            return TimeUtils.formatDateTime2String(time);
        } else if (value instanceof Date) {
            LocalDateTime time = TimeUtils.convert2LocalDateTime((Date) value);
            return TimeUtils.formatDateTime2String(time);
        }
        return value.toString();
    }
}
