package com.consolefire.relayer.core.data.query.internal;

import com.consolefire.relayer.core.data.query.QueryType;

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

}
