package com.consolefire.relayer.core.data.query.exec;

import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Collection;

public interface MessageSelectQueryExecutor<ID extends Serializable, M extends Message<ID>, Q extends SelectQuery>
    extends QueryExecutor<Q, Collection<M>> {

}
