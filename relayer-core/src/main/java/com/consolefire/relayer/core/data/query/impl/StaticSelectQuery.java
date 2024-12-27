package com.consolefire.relayer.core.data.query.impl;


import com.consolefire.relayer.core.data.query.QueryType;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.data.query.internal.DefaultStaticQuery;

public final class StaticSelectQuery extends DefaultStaticQuery implements SelectQuery {

    public StaticSelectQuery(String statement) {
        super(QueryType.SELECT, statement);
    }


}
