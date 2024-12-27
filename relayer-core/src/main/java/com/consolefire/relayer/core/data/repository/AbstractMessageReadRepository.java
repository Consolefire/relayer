package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.core.data.utils.MessageQueryProperties;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractMessageReadRepository<ID extends Serializable, M extends Message<ID>>
    implements MessageReadRepository<ID, M> {

    @Override
    public Collection<M> findAll() {
        return List.of();
    }

    @Override
    public Optional<M> findByMessageId(ID messageId) {
        return Optional.empty();
    }

    @Override
    public Collection<M> findByGroupId(String groupId) {
        return List.of();
    }

    @Override
    public Collection<M> query(MessageQueryProperties<ID> messageQueryProperties) {
        return List.of();
    }
}
