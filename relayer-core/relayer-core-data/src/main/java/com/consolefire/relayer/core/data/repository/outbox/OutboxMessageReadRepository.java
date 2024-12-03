package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.repository.MessageRepository;
import com.consolefire.relayer.core.data.utils.MessageFilterProperties;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;
import java.util.Collection;

public interface OutboxMessageReadRepository<ID extends Serializable, M extends OutboundMessage<ID>> //
    extends MessageRepository<ID, M> {

    Collection<M> findProcessableMessages(MessageFilterProperties<ID> filterProperties);

}
