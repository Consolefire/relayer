package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.util.query.SelectQuery;
import com.consolefire.relayer.core.data.utils.MessageQueryProperties;
import java.io.Serializable;

public interface OutboundMessageQueryProvider {

    <ID extends Serializable> SelectQuery getProcessableMessageSelectQuery(
        MessageQueryProperties<ID> filterProperties);
}
