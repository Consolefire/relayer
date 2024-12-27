package com.consolefire.relayer.core.data.query;

public interface QueryProvider<Q extends Query> {

    Q getQuery();

}
