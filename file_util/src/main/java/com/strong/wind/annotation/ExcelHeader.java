package com.strong.wind.annotation;



import com.strong.wind.poiExcel.ExcelCellValueHandler;

import java.lang.annotation.*;

/**
 * 借鉴参考easyexcel
 * @author wl
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelHeader {

    String[] value() default {""};

    String dictType() default "";

    Class<? extends ExcelCellValueHandler> handler() default ExcelCellValueHandler.class;
}
