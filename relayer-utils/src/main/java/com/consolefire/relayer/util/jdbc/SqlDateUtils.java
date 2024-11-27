package com.consolefire.relayer.util.jdbc;

public class SqlDateUtils {

    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
}
