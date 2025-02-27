package com.consolefire.relayer.core.data.query;

import com.consolefire.relayer.util.query.SelectQuery;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.util.query.exec.QueryExecutor;
import java.io.Serializable;
import java.util.Collection;

public interface MessageSelectQueryExecutor<ID extends Serializable, M extends Message<ID>, Q extends SelectQuery>
    extends QueryExecutor<Q, Collection<M>> {

}
