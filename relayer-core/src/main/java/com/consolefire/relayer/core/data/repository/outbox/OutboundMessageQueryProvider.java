package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.data.utils.MessageQueryProperties;
import java.io.Serializable;

public interface OutboundMessageQueryProvider {

    <ID extends Serializable> SelectQuery getProcessableMessageSelectQuery(
        MessageQueryProperties<ID> filterProperties);
}
