package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.MessageRepository;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;

public interface OutboundMessageReadRepository<ID extends Serializable> //
    extends MessageRepository<ID, OutboundMessage<ID>> {


}
