package com.consolefire.relayer.core.data.query.builder;

import com.consolefire.relayer.core.data.query.Query;

public interface QueryBuilder<Q extends Query> {

    Q build();

}
