package com.consolefire.relayer.util.query;

public record ParameterDefinition(String columnName, int parameterIndex) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterDefinition that = (ParameterDefinition) o;
        return parameterIndex == that.parameterIndex && columnName.equals(that.columnName);
    }

    @Override
    public int hashCode() {
        int result = columnName.hashCode();
        result = 31 * result + parameterIndex;
        return result;
    }

    @Override
    public String toString() {
        return "ParameterDefinition{" +
            "columnName='" + columnName + '\'' +
            ", parameterIndex=" + parameterIndex +
            '}';
    }
}
