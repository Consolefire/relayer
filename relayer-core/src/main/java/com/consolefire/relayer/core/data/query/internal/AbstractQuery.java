package com.consolefire.relayer.core.data.query.internal;

import com.consolefire.relayer.core.data.query.Query;
import com.consolefire.relayer.core.data.query.QueryType;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractQuery implements Query {

    private final QueryType type;
    private final ConcurrentHashMap<Integer, Object> values = new ConcurrentHashMap<>();


    public AbstractQuery(QueryType type) {
        if (type == null) {
            throw new IllegalArgumentException("QueryType cannot be null");
        }
        this.type = type;
    }

    @Override
    public QueryType getType() {
        return type;
    }

    @Override
    public final String toStatement() {
        return buildStatement();
    }

    protected abstract String buildStatement();

    @Override
    public final String resolvedStatement() {
        return resolveStatement();
    }

    protected abstract String resolveStatement();

    @Override
    public final <V> V getParameterValue(int index) {
        return (V) values.get(index);
    }

    @Override
    public final <V> void setParameterValue(int index, V value) {
        values.put(index, value);
    }

}
