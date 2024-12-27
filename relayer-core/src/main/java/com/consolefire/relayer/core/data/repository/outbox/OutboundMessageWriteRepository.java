package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.repository.MessageWriteRepository;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;

public interface OutboundMessageWriteRepository<ID extends Serializable>
    extends MessageWriteRepository<ID, OutboundMessage<ID>> {

}
