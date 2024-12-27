package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import java.io.Serializable;

public interface MessageWriteRepository<ID extends Serializable, M extends Message<ID>>
    extends MessageRepository<ID, M> {

    M save(M message);

    M saveOrUpdate(M message);

    M update(M message);

    M updateState(ID messageId, MessageState state);

    boolean delete(ID messageId);

    int deleteForGroup(String groupId);
}
