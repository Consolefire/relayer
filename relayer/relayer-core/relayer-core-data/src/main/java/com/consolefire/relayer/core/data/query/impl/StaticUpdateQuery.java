package com.consolefire.relayer.core.data.query.impl;


import com.consolefire.relayer.core.data.query.QueryType;
import com.consolefire.relayer.core.data.query.UpdateQuery;
import com.consolefire.relayer.core.data.query.internal.DefaultStaticQuery;

public final class StaticUpdateQuery extends DefaultStaticQuery implements UpdateQuery {


    public StaticUpdateQuery(String statement) {
        super(QueryType.UPDATE, statement);
    }

}
