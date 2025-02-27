package com.consolefire.relayer.util.query;

import java.util.Objects;

public record ParameterDefinition(String columnName, String parameterName, int parameterIndex) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterDefinition that = (ParameterDefinition) o;
        return parameterIndex == that.parameterIndex && Objects.equals(columnName, that.columnName)
            && Objects.equals(parameterName, that.parameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, parameterName, parameterIndex);
    }
}
