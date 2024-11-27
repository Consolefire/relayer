package com.consolefire.relayer.util.jdbc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.sql.JDBCType;

@Getter
@RequiredArgsConstructor
public enum JavaJdbcTypeMap {

    LONG(JDBCType.BIGINT, Long.class),
    UUID(JDBCType.VARCHAR, String.class),
    TEXT(JDBCType.LONGNVARCHAR, String.class),
    ;

    private final JDBCType jdbcType;
    private final Type javaType;


}
