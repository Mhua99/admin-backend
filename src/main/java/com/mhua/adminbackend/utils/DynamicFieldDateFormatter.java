package com.mhua.adminbackend.utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DynamicFieldDateFormatter {

    // 定义两种格式
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 对 List<Map> 中所有行的所有字段进行自动时间识别和格式化
     */
    public static void formatDateTimeFields(List<Map<String, Object>> dataList) {
        for (Map<String, Object> row : dataList) {
            formatDateTimeFieldsInRow(row);
        }
    }

    /**
     * 对单行数据中的所有字段进行自动时间识别和格式化
     */
    public static void formatDateTimeFieldsInRow(Map<String, Object> row) {
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                entry.setValue(formatIfTime(value));
            }
        }
    }

    /**
     * 根据对象类型返回对应的格式化字符串
     */
    public static Object formatIfTime(Object obj) {
        if (obj == null) {
            return null;
        }

        // 处理 LocalDate 或 java.sql.Date（date 类型）
        if (obj instanceof LocalDate) {
            return DATE_FORMATTER.format((LocalDate) obj);
        } else if (obj instanceof java.sql.Date) {
            return DATE_FORMATTER.format(((java.sql.Date) obj).toLocalDate());
        }

        // 处理 LocalDateTime、Timestamp、java.util.Date（datetime 类型）
        else if (obj instanceof LocalDateTime) {
            return DATETIME_FORMATTER.format((LocalDateTime) obj);
        } else if (obj instanceof Timestamp) {
            return DATETIME_FORMATTER.format(((Timestamp) obj).toLocalDateTime());
        } else if (obj instanceof java.util.Date) {
            return DATETIME_FORMATTER.format(((java.util.Date) obj).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }

        // 处理字符串格式的时间
        else if (obj instanceof String && isISO8601DateString((String) obj)) {
            try {
                LocalDateTime dt = LocalDateTime.parse(((String) obj).replace(" ", "T"));
                return dt.format(DATETIME_FORMATTER);
            } catch (Exception ignored) {}
        } else if(obj instanceof Integer) {
            return  obj;
        }

        // 其他类型返回原字符串
        return obj.toString();
    }

    /**
     * 简单判断字符串是否是 ISO 8601 时间格式
     */
    private static boolean isISO8601DateString(String str) {
        return str.matches("\\d{4}-\\d{2}-\\d{2}(T|\\s)\\d{2}:\\d{2}(:\\d{2})?(\\.\\d+)?");
    }
}
