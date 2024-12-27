package com.consolefire.relayer.core.data.query.impl;


import com.consolefire.relayer.core.data.query.DeleteQuery;
import com.consolefire.relayer.core.data.query.QueryType;
import com.consolefire.relayer.core.data.query.internal.DefaultStaticQuery;

public final class StaticDeleteQuery extends DefaultStaticQuery implements DeleteQuery {

    public StaticDeleteQuery(String statement) {
        super(QueryType.DELETE, statement);
    }

}
