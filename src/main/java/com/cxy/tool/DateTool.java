package com.cxy.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CXY
 * @className DateTool
 * @description 时间处理相关
 * @date 2025/02/21 10:17
 */
public class DateTool {

    public enum FormatSymbol {
        Time("yyyy-MM-dd HH:mm:ss"),
        Day("yyyy-MM-dd");
        private String symbol;

        FormatSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return this.symbol;
        }
    }

    public static String format(Long timeMillis, FormatSymbol formatSymbol) {
        SimpleDateFormat fmt = new SimpleDateFormat(formatSymbol.getSymbol());
        Date date = new Date(timeMillis);
        return fmt.format(date);
    }

    public static String format(Date date, FormatSymbol formatSymbol) {
        SimpleDateFormat fmt = new SimpleDateFormat(formatSymbol.getSymbol());
        return fmt.format(date);
    }

    public static Date parse(String dateString, FormatSymbol formatSymbol) {
        SimpleDateFormat fmt = new SimpleDateFormat(formatSymbol.getSymbol());
        try {
            return fmt.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parse(Long timeMillis) {
        return new Date(timeMillis);
    }

    /**
     * 倒推年/季度/月份
     *
     * @param year  年
     * @param start 起始值
     * @param total 倒推个数
     * @param key   0年 12月份 4季度
     * @return
     * @author CXY
     * @date 2025/02/21 10:18
     */
    public static Map<String, Object> backwardsCore(String year, String start, int total, int key) {
        Map<String, Object> map = new HashMap<String, Object>();
        int y = Integer.parseInt(year);
        int m = Integer.parseInt(start);
        if (key != 0 && m > key) {
            //起始值不能大于key
            return map;
        }
        StringBuilder monthBuilder = new StringBuilder(start);
        map.put(String.valueOf(y), monthBuilder.toString());
        if (total > 0) {
            for (int i = 1; i < total; i++) {
                m--;
                if (m == 0) {
                    y--;
                    m = key;
                    monthBuilder = new StringBuilder(m);
                }
                if (m != key) {
                    monthBuilder.append(",");
                }
                monthBuilder.append(m);
                map.put(String.valueOf(y), monthBuilder.toString());
            }
        }
        return map;
    }

}
