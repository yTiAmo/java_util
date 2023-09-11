package com.strong.wind.poiExcel;


import com.strong.wind.annotation.ExcelHeader;

/**
 * @author wanglei
 * @date 2022年06月07日 11:17
 */
public class DictTypeHandler implements ExcelCellValueHandler {

    @Override
    public String handle(Object value, ExcelHeader annotation) {
        String name = (String) value;
//        if (value != null) {
//            if (annotation.dictType().equals(CommonConstant.CodeToName.AREA_INVOLVED.getCode())){
//                name = RedisDataUtil.getOrgName(String.valueOf(value));
//            }else {
//                name = RedisDataUtil.queryAndSaveDictByKey(annotation.dictType(),String.valueOf(value));
//            }
//        }
        return name;
    }
}
