package com.consolefire.relayer.util.query;

import java.util.Objects;

public record ColumnDefinition(String columnName, int columnIndex) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnDefinition that = (ColumnDefinition) o;
        return columnIndex == that.columnIndex && Objects.equals(columnName, that.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, columnIndex);
    }
}
