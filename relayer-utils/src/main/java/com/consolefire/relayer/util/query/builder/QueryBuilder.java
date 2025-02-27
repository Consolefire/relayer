package com.consolefire.relayer.util.query.builder;

import com.consolefire.relayer.util.query.Query;

public interface QueryBuilder<Q extends Query> {

    Q build();

}
