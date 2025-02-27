package com.consolefire.relayer.util.query.internal;

import com.consolefire.relayer.util.query.QueryType;

public class DefaultStaticQuery extends AbstractQuery {

    private final String statement;

    public DefaultStaticQuery(QueryType type, String statement) {
        super(type);
        this.statement = statement;
    }

    @Override
    protected final String buildStatement() {
        return statement;
    }

    @Override
    protected String resolveStatement() {
        return statement;
    }
}
