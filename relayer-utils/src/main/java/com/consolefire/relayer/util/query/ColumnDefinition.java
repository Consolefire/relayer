package com.consolefire.relayer.util.query;

public record ColumnDefinition(String columnName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnDefinition that = (ColumnDefinition) o;
        return columnName.equals(that.columnName);
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
            "columnName='" + columnName + '\'' +
            '}';
    }
}
