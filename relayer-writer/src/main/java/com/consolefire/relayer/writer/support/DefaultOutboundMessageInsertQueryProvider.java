package com.consolefire.relayer.writer.support;

import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.core.data.query.internal.DefaultInsertQuery;
import com.consolefire.relayer.util.jdbc.ColumnInfo;
import com.consolefire.relayer.util.jdbc.JavaFieldInfo;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DefaultOutboundMessageInsertQueryProvider implements MessageInsertQueryProvider {

    private final String schemaName;
    private final String tableName;
    private final Map<JavaFieldInfo, ColumnInfo> columns;


    public DefaultOutboundMessageInsertQueryProvider(String schemaName, @NonNull String tableName,
                                                     @NonNull Map<JavaFieldInfo, ColumnInfo> columns) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public InsertQuery getQuery() {
        Map<String, Integer> columnIndexes = new HashMap<>();
        this.columns.values().forEach(ci -> columnIndexes.put(ci.columnName(), ci.serialNumber()));
        return new DefaultInsertQuery(schemaName, tableName, columnIndexes);
    }

    public Map<String, String> getFieldColumnMappings() {
        Map<String, String> columnMappings = new HashMap<>();
        this.columns.forEach((fi, ci) -> columnMappings.put(fi.fieldName(), ci.columnName()));
        return columnMappings;
    }

}
