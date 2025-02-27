package com.consolefire.relayer.util.query;

public interface ParameterizedQueryProvider<Q extends ParameterizedQuery> extends QueryProvider<Q> {

    int getParameterCount();

}
