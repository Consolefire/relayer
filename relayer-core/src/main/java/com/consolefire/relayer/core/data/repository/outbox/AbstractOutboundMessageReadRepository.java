package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.query.Parameter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.data.repository.AbstractMessageReadRepository;
import com.consolefire.relayer.core.data.utils.MessageQueryProperties;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOutboundMessageReadRepository<ID extends Serializable>
    extends AbstractMessageReadRepository<ID, OutboundMessage<ID>>
    implements OutboundMessageReadRepository<ID> {

    private final OutboundMessageQueryProvider outboundMessageQueryProvider;
    private final OutboundMessageSelectQueryExecutor<ID> outboundMessageSelectQueryExecutor;

    public Collection<OutboundMessage<ID>> findProcessableMessages(MessageQueryProperties<ID> queryProperties) {
        log.debug("Find processable messages with filter: {}", queryProperties);
        SelectQuery processableMessageSelectQuery = outboundMessageQueryProvider.getProcessableMessageSelectQuery(
            queryProperties);
        if (null == processableMessageSelectQuery) {
            throw new RuntimeException("No filter query");
        }
        List<Parameter<?>> parameters = createParameters(processableMessageSelectQuery, queryProperties);
        Collection<OutboundMessage<ID>> messages = outboundMessageSelectQueryExecutor.execute(getDataSource(),
            processableMessageSelectQuery,
            parameters);
        return messages;
    }

    protected abstract DataSource getDataSource();

    private List<Parameter<?>> createParameters(SelectQuery processableMessageSelectQuery,
        MessageQueryProperties<ID> filterProperties) {
        return null;
    }
}
