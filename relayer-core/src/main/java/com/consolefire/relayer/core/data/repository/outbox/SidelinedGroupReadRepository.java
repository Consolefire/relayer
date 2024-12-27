package com.consolefire.relayer.core.data.repository.outbox;

import com.consolefire.relayer.core.data.repository.MessageReadRepository;
import com.consolefire.relayer.model.outbox.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedGroupReadRepository<ID extends Serializable>
    extends MessageReadRepository<ID, SidelinedMessage<ID>> {

}
