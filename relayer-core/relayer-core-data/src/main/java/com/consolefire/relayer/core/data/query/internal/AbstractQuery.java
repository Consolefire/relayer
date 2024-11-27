package com.consolefire.relayer.core.data.query.internal;

import com.consolefire.relayer.core.data.query.Query;
import com.consolefire.relayer.core.data.query.QueryType;

public abstract class AbstractQuery implements Query {

    private final QueryType type;

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
}
