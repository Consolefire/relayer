package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.MessageReadRepository;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedGroupReadRepository<ID extends Serializable>
    extends MessageReadRepository<ID, SidelinedMessage<ID>> {

}
