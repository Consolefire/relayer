package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.MessageWriteRepository;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;

public interface OutboundMessageWriteRepository<ID extends Serializable>
    extends MessageWriteRepository<ID, OutboundMessage<ID>> {

}
