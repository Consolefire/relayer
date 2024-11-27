package com.consolefire.relayer.util.jdbc;

import lombok.Builder;
import lombok.NonNull;
import lombok.With;

import java.lang.reflect.Type;
import java.sql.JDBCType;

@With
@Builder
public record ColumnInfo(
        @NonNull String columnName,
        @NonNull Type javaType,
        @NonNull JDBCType jdbcType,
        int serialNumber,
        int length) implements Comparable<ColumnInfo> {

    public String parameterName() {
        return "param_" + columnName;
    }

    @Override
    public int compareTo(ColumnInfo o) {
        return Integer.compare(serialNumber, o.serialNumber);
    }
}
