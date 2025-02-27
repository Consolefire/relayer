package com.consolefire.relayer.util.query;

import java.util.Map;

public interface InsertQuery extends Query {

    int indexOf(String columnName);

    int getParameterCount();

    Map<String, Integer> getColumnIndexes();
}
