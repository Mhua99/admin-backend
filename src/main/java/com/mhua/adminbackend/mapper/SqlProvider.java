package com.mhua.adminbackend.mapper;

import java.util.Map;

public class SqlProvider {

    public String createTableSql(StringBuilder sql) {
        return sql.toString();
    }

    public String dropPhysicalTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }
}