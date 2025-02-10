package com.consolefire.relayer.core.data.query.builder;


import com.consolefire.relayer.core.data.query.Query;
import com.consolefire.relayer.core.data.query.QueryType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractQueryBuilder<Q extends Query, B extends QueryBuilder<Q>> implements QueryBuilder<Q> {

    protected static final String PARAM_NAME_PREFIX = "param_";
    protected static final int INITIAL_INDEX = 0;
    protected final QueryType queryType;
    protected final String prefix;
    protected final Map<String, Integer> columns;
    protected final Set<String> parameters;
    private final AtomicInteger indexer;
    protected String tableName;

    public AbstractQueryBuilder(QueryType queryType, String prefix) {
        this.queryType = queryType;
        this.prefix = prefix;
        this.indexer = new AtomicInteger(INITIAL_INDEX);
        this.columns = new HashMap<>();
        this.parameters = new LinkedHashSet<>();
    }

    protected Integer nextIndex() {
        return indexer.incrementAndGet();
    }

    public B withTableName(String tableName) {
        this.tableName = tableName;
        return self();
    }

    protected abstract B self();

    protected abstract Q createInstance();

    protected abstract void fillProperties(Q query);

    @Override
    public Q build() {
        Q query = createInstance();
        fillProperties(query);
        return query;
    }


}
