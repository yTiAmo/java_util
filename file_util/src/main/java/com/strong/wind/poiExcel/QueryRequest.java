package com.strong.wind.poiExcel;


import java.io.Serializable;

/**
 * @author MrBird
 */
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    /**
     * 当前页面数据量
     */
    private Integer pageSize;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public String getField() {
        return field;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", field='" + field + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
