package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.ParkedMessageReadRepository;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedMessageReadRepository<ID extends Serializable>
    extends ParkedMessageReadRepository<ID, OutboundMessage<ID>, SidelinedMessage<ID>> {

}
