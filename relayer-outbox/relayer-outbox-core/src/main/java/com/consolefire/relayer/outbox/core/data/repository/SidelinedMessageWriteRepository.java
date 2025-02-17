package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.ParkedMessageWriteRepository;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedMessageWriteRepository<ID extends Serializable>
    extends ParkedMessageWriteRepository<ID, OutboundMessage<ID>, SidelinedMessage<ID>> {

}
