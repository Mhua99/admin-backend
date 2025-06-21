package com.mhua.adminbackend.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateFormatterUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 格式化 Map 列表中的时间字段为字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dataList      数据列表
     * @param fieldNames    要格式化的时间字段名
     */
    public static void formatTimeFields(List<Map<String, Object>> dataList, String... fieldNames) {
        if (dataList == null || fieldNames == null || fieldNames.length == 0) {
            return;
        }

        for (Map<String, Object> row : dataList) {
            for (String fieldName : fieldNames) {
                if (row.containsKey(fieldName)) {
                    Object value = row.get(fieldName);

                    if (value instanceof LocalDateTime) {
                        row.put(fieldName, ((LocalDateTime) value).format(DEFAULT_FORMATTER));
                    } else if (value instanceof java.sql.Timestamp) {
                        LocalDateTime time = ((java.sql.Timestamp) value).toLocalDateTime();
                        row.put(fieldName, time.format(DEFAULT_FORMATTER));
                    } else if (value instanceof Long) {
                        LocalDateTime time = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli((Long) value),
                                ZoneId.systemDefault()
                        );
                        row.put(fieldName, time.format(DEFAULT_FORMATTER));
                    }
                    // 可选：支持 java.util.Date
                    else if (value instanceof java.util.Date) {
                        LocalDateTime time = ((java.util.Date) value).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        row.put(fieldName, time.format(DEFAULT_FORMATTER));
                    }
                }
            }
        }
    }

    /**
     * 将任意时间对象格式化为 yyyy-MM-dd HH:mm:ss
     *
     * @param value 时间值，支持 LocalDateTime、Timestamp、Date、Long（时间戳）
     * @return 格式化后的时间字符串
     */
    public static String formatIfTime(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DEFAULT_FORMATTER);
        } else if (value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime().format(DEFAULT_FORMATTER);
        } else if (value instanceof Date) {
            return ((Date) value).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DEFAULT_FORMATTER);
        } else if (value instanceof Long) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli((Long) value),
                    ZoneId.systemDefault()
            ).format(DEFAULT_FORMATTER);
        }

        // 如果不是时间类型，原样返回
        return value.toString();
    }
}
