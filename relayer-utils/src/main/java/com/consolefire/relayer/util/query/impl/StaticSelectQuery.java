package com.consolefire.relayer.util.query.impl;


import com.consolefire.relayer.util.query.QueryType;
import com.consolefire.relayer.util.query.SelectQuery;
import com.consolefire.relayer.util.query.internal.DefaultStaticQuery;

public final class StaticSelectQuery extends DefaultStaticQuery implements SelectQuery {

    public StaticSelectQuery(String statement) {
        super(QueryType.SELECT, statement);
    }


}
