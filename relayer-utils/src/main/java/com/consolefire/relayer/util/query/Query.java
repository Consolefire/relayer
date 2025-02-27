package com.consolefire.relayer.util.query;

public interface Query {

    QueryType getType();

    String toStatement();

    String resolvedStatement();

    <V> V getParameterValue(int index);

    <V> void setParameterValue(int index, V value);

}
