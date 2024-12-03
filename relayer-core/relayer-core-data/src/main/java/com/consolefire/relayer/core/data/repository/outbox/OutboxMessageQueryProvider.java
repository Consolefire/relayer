package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.data.utils.MessageFilterProperties;
import java.io.Serializable;

public interface OutboxMessageQueryProvider {

    <ID extends Serializable> SelectQuery getProcessableMessageSelectQuery(
        MessageFilterProperties<ID> filterProperties);
}
