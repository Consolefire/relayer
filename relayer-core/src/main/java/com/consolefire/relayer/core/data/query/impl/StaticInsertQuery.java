package com.consolefire.relayer.core.data.query.impl;


import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.core.data.query.QueryType;
import com.consolefire.relayer.core.data.query.internal.DefaultStaticQuery;

import java.util.HashMap;
import java.util.Map;

public final class StaticInsertQuery extends DefaultStaticQuery implements InsertQuery {

    private final Map<String, Integer> columnIndices;

    public StaticInsertQuery(String statement) {
        this(statement, new HashMap<>());

    }

    public StaticInsertQuery(String statement, Map<String, Integer> columnIndices) {
        super(QueryType.INSERT, statement);
        this.columnIndices = columnIndices;
    }

    @Override
    public int indexOf(String columnName) {
        return this.columnIndices.containsKey(columnName)
                ? this.columnIndices.get(columnName)
                : -1;
    }

    @Override
    public int getParameterCount() {
        return columnIndices.keySet().size();
    }

    @Override
    public Map<String, Integer> getColumnIndexes() {
        return columnIndices;
    }
}
