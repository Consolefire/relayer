package com.consolefire.relayer.core.data.query;

public interface Query {

    QueryType getType();

    String toStatement();

}
