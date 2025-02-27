package com.consolefire.relayer.util.query.impl;


import com.consolefire.relayer.util.query.DeleteQuery;
import com.consolefire.relayer.util.query.QueryType;
import com.consolefire.relayer.util.query.internal.DefaultStaticQuery;

public final class StaticDeleteQuery extends DefaultStaticQuery implements DeleteQuery {

    public StaticDeleteQuery(String statement) {
        super(QueryType.DELETE, statement);
    }

}
