package com.consolefire.relayer.core.data.query;

public interface ParameterizedQueryProvider<Q extends ParameterizedQuery> extends QueryProvider<Q> {

    int getParameterCount();

}
