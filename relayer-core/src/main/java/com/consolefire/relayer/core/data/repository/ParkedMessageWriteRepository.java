package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.ParkedMessage;
import java.io.Serializable;

public interface ParkedMessageWriteRepository<ID extends Serializable, M extends Message<ID>, PM extends ParkedMessage<ID, M>>
    extends ParkedMessageRepository<ID, M, PM> {

    M save(M message);

    M saveOrUpdate(M message);

    M update(M message);

    M updateState(ID messageId, MessageState state);

    boolean delete(ID messageId);

    int deleteForGroup(String groupId);
}
