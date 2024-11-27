package com.consolefire.relayer.core.data.query;

import java.util.Map;

public interface InsertQuery extends Query {

    int indexOf(String columnName);

    int getParameterCount();

    Map<String, Integer> getColumnIndexes();
}
