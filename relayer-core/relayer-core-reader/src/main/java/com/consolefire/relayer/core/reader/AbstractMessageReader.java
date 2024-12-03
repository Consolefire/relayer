package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.repository.MessageRepository;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractMessageReader<ID extends Serializable, M extends Message<ID>> implements MessageReader<ID, M> {

    private final MessageRepository<ID, M> messageRepository;

    @Override
    public Collection<M> read(MessageSourceProperties messageSourceProperties) {

        return List.of();
    }
}
