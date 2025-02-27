package com.consolefire.relayer.util.query.impl;


import com.consolefire.relayer.util.query.QueryType;
import com.consolefire.relayer.util.query.UpdateQuery;
import com.consolefire.relayer.util.query.internal.DefaultStaticQuery;

public final class StaticUpdateQuery extends DefaultStaticQuery implements UpdateQuery {


    public StaticUpdateQuery(String statement) {
        super(QueryType.UPDATE, statement);
    }

}
