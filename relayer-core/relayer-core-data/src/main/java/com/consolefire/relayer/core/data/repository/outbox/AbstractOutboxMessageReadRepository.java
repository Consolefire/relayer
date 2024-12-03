package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.query.Parameter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.data.repository.AbstractMessageReadRepository;
import com.consolefire.relayer.core.data.utils.MessageFilterProperties;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractOutboxMessageReadRepository<ID extends Serializable>
    extends AbstractMessageReadRepository<ID, OutboundMessage<ID>>
    implements OutboxMessageReadRepository<ID, OutboundMessage<ID>> {

    private final DataSource dataSource;
    private final OutboxMessageQueryProvider outboxMessageQueryProvider;
    private final OutboundMessageSelectQueryExecutor<ID> outboundMessageSelectQueryExecutor;

    @Override
    public Collection<OutboundMessage<ID>> findProcessableMessages(MessageFilterProperties<ID> filterProperties) {
        log.debug("Find processable messages with filter: {}", filterProperties);
        SelectQuery processableMessageSelectQuery = outboxMessageQueryProvider.getProcessableMessageSelectQuery(
            filterProperties);
        if (null == processableMessageSelectQuery) {
            throw new RuntimeException("No filter query");
        }
        List<Parameter<?>> parameters = createParameters(processableMessageSelectQuery, filterProperties);
        Collection<OutboundMessage<ID>> messages = outboundMessageSelectQueryExecutor.execute(dataSource,
            processableMessageSelectQuery,
            parameters);
        return messages;
    }

    private List<Parameter<?>> createParameters(SelectQuery processableMessageSelectQuery,
        MessageFilterProperties<ID> filterProperties) {
        return null;
    }
}
