package com.mhua.adminbackend.mapper;

import java.util.Map;
import java.util.Set;

public class SqlProvider {

    public String createTableSql(StringBuilder sql) {
        return sql.toString();
    }

    public String dropPhysicalTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public String searchTableSql(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        Map<String, Object> conditions = (Map<String, Object>) params.get("conditions");

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ").append(tableName);

        if (conditions != null && !conditions.isEmpty()) {
            sqlBuilder.append(" WHERE ");
            Set<String> keys = conditions.keySet();
            int count = 0;
            for (String key : keys) {
                Object value = conditions.get(key);

                // 过滤掉 null 或 空字符串 的条件
                if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                    continue;
                }

                if (count > 0) {
                    sqlBuilder.append(" AND ");
                }
                sqlBuilder.append(key).append(" = #{conditions[").append(key).append("]}");
                count++;
            }

            // 如果所有条件都被过滤掉了，去掉 WHERE 子句避免语法错误
            if (count == 0) {
                int whereIndex = sqlBuilder.indexOf(" WHERE ");
                if (whereIndex > -1) {
                    sqlBuilder.delete(whereIndex, whereIndex + " WHERE ".length());
                }
            }
        }

        return sqlBuilder.toString();
    }
    public String insertDataSql(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        Map<String, Object> data = (Map<String, Object>) params.get("data");

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("插入数据不能为空");
        }

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        int count = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() == null) continue; // 忽略 null 值字段
            if (count > 0) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(entry.getKey());
            values.append("#{data[").append(entry.getKey()).append("]}");
            count++;
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }

    public String updateDataSql(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        Map<String, Object> data = (Map<String, Object>) params.get("data");
        Object id = params.get("id");

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("更新数据不能为空");
        }
        if (id == null) {
            throw new IllegalArgumentException("主键 id 不能为空");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");

        int count = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 忽略 null 值字段 和 "id" 字段
            if ("id".equalsIgnoreCase(key) || value == null || "createTime".equalsIgnoreCase(key)) {
                continue;
            }

            if (count > 0) {
                sqlBuilder.append(", ");
            }
            sqlBuilder.append(key).append(" = #{data[").append(key).append("]}");
            count++;
        }

        // 确保至少有一个字段被更新
        if (count == 0) {
            throw new IllegalArgumentException("没有可更新的字段");
        }

        sqlBuilder.append(" WHERE id = #{id}");
        return sqlBuilder.toString();
    }

}