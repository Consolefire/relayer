package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.core.data.utils.MessageQueryProperties;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.ParkedMessage;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface ParkedMessageReadRepository<ID extends Serializable, M extends Message<ID>, PM extends ParkedMessage<ID, M>>
    extends ParkedMessageRepository<ID, M, PM> {

    Collection<M> findAll();

    Optional<M> findByMessageId(ID messageId);

    Collection<M> findByGroupId(String groupId);

    Collection<M> query(MessageQueryProperties<ID> messageQueryProperties);
}
