package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.MessageWriteRepository;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedGroupWriteRepository<ID extends Serializable>
    extends MessageWriteRepository<ID, SidelinedMessage<ID>> {

}
