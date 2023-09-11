package com.strong.wind.annotation;

import java.lang.annotation.*;

/**
 * 借鉴参考easyexcel
 * @author wanglei
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnWidth {
    int value() default -1;
}
