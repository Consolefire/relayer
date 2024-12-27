package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.repository.MessageWriteRepository;
import com.consolefire.relayer.model.outbox.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedMessageWriteRepository<ID extends Serializable>
    extends MessageWriteRepository<ID, SidelinedMessage<ID>> {

}
