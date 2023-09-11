package com.strong.wind.poiExcel;

import lombok.Data;

import java.io.Serializable;

/**
 * ExcelRecord
 * @Author: wanglei
 * @Date 2022-06-02
 * @Description:
 */
@Data
public class ExcelRecord<E> implements Serializable {
    private static final long serialVersionUID = -7303036218518657604L;
    /**
     * 最小id
     */
    private E minId;
    /**
     * 最大id
     */
    private E maxId;
}
