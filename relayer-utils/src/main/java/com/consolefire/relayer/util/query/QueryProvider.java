package com.consolefire.relayer.util.query;

public interface QueryProvider<Q extends Query> {

    Q getQuery();

}
