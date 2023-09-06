package org.strong.wind;

import cn.hutool.core.util.EnumUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王蕾
 * @创建时间 2023/9/6 0006 17:24 周三
 * @描述
 */
public class CreateJavaModel {

    public static void pgsqlToJava(String text){

        Map<String,String> fieldAndType = new HashMap<>();
        Map<String,String> fieldComment = new HashMap<>();

        Map<String,PgsqlToJavaEnum> enumMap = EnumUtil.getEnumMap(PgsqlToJavaEnum.class);
        enumMap.forEach((k,v)->{
            String fieldPattern = "\"(\\w+)\" "+v.getPgsqlType()+"";
            Pattern pattern = Pattern.compile(fieldPattern );
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String columnName1 = matcher.group(1); // 匹配到的列名，例如 "rid" 或 "cym"
                System.out.println("列名：" + columnName1);
            }
        });
    }

    public static void sgetFieldAndComment(String text){
        String commentPattern = "\"([^\"]+)\" IS '([^\"]+)'";
        Pattern pattern = Pattern.compile(commentPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String columnName = matcher.group(1); // 匹配到的列名，例如 "rid" 或 "cym"
            String comment = matcher.group(2);    // 匹配到的注释，例如 "人ID" 或 "曾用名"
            System.out.println("列名：" + columnName);
            System.out.println("注释：" + comment);
        }
    }
    public static void main(String[] args) {
        String input = "CREATE TABLE \"public\".\"stg_qdsjzx_ryjbxx_yh_df\" (\n" +
                "  \"rid\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"cym\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"is_kgh\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"syrkzlbdm\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"syrkzlbhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjzwmc\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjzwmc\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"flag\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"is_zdry\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"etltime\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"dsjzx_taskid\" timestamp(6),\n" +
                "  \"qhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xbdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xbhz\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"mzdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"mzhz\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"csrq\" timestamp(6),\n" +
                "  \"csdxzqhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"csdxzqhhz\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"csdxz\" text COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jgdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"syrklbdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jghz\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hh\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hlsh\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"fhlsh\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdxzqhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdxzqhhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdlmdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdlmhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdxz\" text COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdz\" text COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"syrklbhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdyjmp\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdyjmpdsh\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdejmp\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdejmpdsh\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdxzqhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdxzqhhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdlmdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdlmhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdxz\" text COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdz\" text COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zjzldm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdyjmp\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdyjmpdsh\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdejmp\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdejmpdsh\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"whcddm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"whcdhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hyzkdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hyzkhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"poxm\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"pozjhm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zjzlhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zy\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zylbdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zylbhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"fwcs\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zjxydm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zjxyhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"byzkdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"byzkhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"sg\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xxdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zjhm\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"xxhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zzsydm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zzsyhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"yhzgxdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"yhzgxhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"yzjzrgxdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"yzjzrgxhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"gjdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"gjhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"ywx\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xm\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"ywm\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jwrysfdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jwrysfhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"tlsydm\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"tlsyhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"rjsj\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xxjb\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zzjgdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zzjghz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"zxgxsj\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xmqp\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"bz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzfwid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjfwid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"mapidd\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdzzjgdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdzzjghz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jlrksj\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjddm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjdhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjwhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"xmsp\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjwhhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjddm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjdhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjwhdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjwhhz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdzzjgdm\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdzzjghz\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"jzdjzwid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjdjzwid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"hjmapid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"yp_taskid\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  CONSTRAINT \"stg_qdsjzx_ryjbxx_yh_df_pkey\" PRIMARY KEY (\"zjhm\")\n" +
                ")\n" +
                ";\n" +
                "\n" +
                "ALTER TABLE \"public\".\"stg_qdsjzx_ryjbxx_yh_df\" \n" +
                "  OWNER TO \"postgres\";\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"rid\" IS '人ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"cym\" IS '曾用名';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"is_kgh\" IS '实有人口子类别代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"syrkzlbdm\" IS '实有人口子类别汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"syrkzlbhz\" IS '居住地建筑物名称';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjzwmc\" IS '户籍地建筑物名称';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjzwmc\" IS '户籍居住地登记人户分离';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"flag\" IS '0初始状态1吸毒2刑释解教';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"is_zdry\" IS '监控时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"etltime\" IS 'ETL时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"dsjzx_taskid\" IS '时间戳';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"qhdm\" IS '区划代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xbdm\" IS '性别代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xbhz\" IS '性别汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"mzdm\" IS '民族代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"mzhz\" IS '民族汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"csrq\" IS '出生日期';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"csdxzqhdm\" IS '出生地行政区划代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"csdxzqhhz\" IS '出生地行政区划汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"csdxz\" IS '出生地详址';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jgdm\" IS '籍贯代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"syrklbdm\" IS '实有人口类别代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jghz\" IS '籍贯汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hh\" IS '户号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hlsh\" IS '户流水号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"fhlsh\" IS '分户流水号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdxzqhdm\" IS '户籍地行政区划代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdxzqhhz\" IS '户籍地行政区划汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdlmdm\" IS '户籍地路名代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdlmhz\" IS '户籍地路名汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdxz\" IS '户籍地详址';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdz\" IS '户籍地址';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"syrklbhz\" IS '实有人口类别汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdyjmp\" IS '户籍地一级门牌';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdyjmpdsh\" IS '户籍地一级门牌单双号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdejmp\" IS '户籍地二级门牌';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdejmpdsh\" IS '户籍地二级门牌单双号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdxzqhdm\" IS '居住地行政区划代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdxzqhhz\" IS '居住地行政区划汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdlmdm\" IS '居住地路名代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdlmhz\" IS '居住地路名汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdxz\" IS '居住地详址';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdz\" IS '居住地址';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zjzldm\" IS '证件种类代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdyjmp\" IS '居住地一级门牌';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdyjmpdsh\" IS '居住地一级门牌单双号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdejmp\" IS '居住地二级门牌';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdejmpdsh\" IS '居住地二级门牌单双号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"whcddm\" IS '文化程度代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"whcdhz\" IS '文化程度汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hyzkdm\" IS '婚姻状况代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hyzkhz\" IS '婚姻状况汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"poxm\" IS '配偶姓名';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"pozjhm\" IS '配偶证件号码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zjzlhz\" IS '证件种类汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zy\" IS '职业';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zylbdm\" IS '职业类别代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zylbhz\" IS '职业类别汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"fwcs\" IS '服务处所';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zjxydm\" IS '宗教信仰代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zjxyhz\" IS '宗教信仰汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"byzkdm\" IS '兵役状况代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"byzkhz\" IS '兵役状况汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"sg\" IS '身高';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xxdm\" IS '血型代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zjhm\" IS '证件号码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xxhz\" IS '血型汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zzsydm\" IS '暂住事由代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zzsyhz\" IS '暂住事由汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"yhzgxdm\" IS '与户主关系代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"yhzgxhz\" IS '与户主关系汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"yzjzrgxdm\" IS '与主居住人关系代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"yzjzrgxhz\" IS '与主居住人关系汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"gjdm\" IS '国家代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"gjhz\" IS '国家汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"ywx\" IS '英文姓';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xm\" IS '姓名';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"ywm\" IS '英文名';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jwrysfdm\" IS '境外人员身份代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jwrysfhz\" IS '境外人员身份汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"tlsydm\" IS '停留事由代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"tlsyhz\" IS '停留事由汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"rjsj\" IS '入境时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xxjb\" IS '信息级别';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zzjgdm\" IS '组织机构代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zzjghz\" IS '组织机构汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"zxgxsj\" IS '最新更新时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xmqp\" IS '姓名全拼';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"bz\" IS '备注';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzfwid\" IS '居住房屋ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjfwid\" IS '户籍房屋ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"mapidd\" IS '地理编码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdzzjgdm\" IS '居住地组织机构代码(户籍人员)';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdzzjghz\" IS '居住地组织机构汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jlrksj\" IS '记录入库时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjddm\" IS '居住地街道代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjdhz\" IS '居住地街道汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjwhdm\" IS '居住地居委会代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"xmsp\" IS '姓名首拼';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjwhhz\" IS '居住地居委会汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjddm\" IS '户籍地街道代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjdhz\" IS '户籍地街道汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjwhdm\" IS '户籍地居委会代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjwhhz\" IS '户籍地居委会汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdzzjgdm\" IS '户籍地组织机构代码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdzzjghz\" IS '户籍地组织机构汉字';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"jzdjzwid\" IS '居住地建筑物ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjdjzwid\" IS '户籍地建筑物ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"hjmapid\" IS '是否空挂户';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"stg_qdsjzx_ryjbxx_yh_df\".\"yp_taskid\" IS 'yp批次号';\n" +
                "\n" +
                "COMMENT ON TABLE \"public\".\"stg_qdsjzx_ryjbxx_yh_df\" IS '人基本信息-殷行治理';";
        sgetFieldAndComment(input);
    }
}
