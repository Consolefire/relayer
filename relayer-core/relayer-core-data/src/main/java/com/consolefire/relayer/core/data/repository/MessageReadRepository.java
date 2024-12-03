package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.core.data.utils.MessageFilterProperties;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface MessageReadRepository<ID extends Serializable, M extends Message<ID>>
    extends MessageRepository<ID, M> {

    Collection<M> findAll();

    Optional<M> findByMessageId(ID messageId);

    Collection<M> findByGroupId(String groupId);

    Collection<M> filter(MessageFilterProperties<ID> messageFilterProperties);
}
