package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.repository.MessageRepository;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;

public interface OutboundMessageReadRepository<ID extends Serializable> //
    extends MessageRepository<ID, OutboundMessage<ID>> {


}
