package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface MessageReader<ID extends Serializable, M extends Message<ID>> {

    default Collection<M> read(MessageSourceProperties messageSourceProperties) {
        return read(messageSourceProperties, Optional.of(MessageFilterProperties.DEFAULT));
    }

    Collection<M> read(MessageSourceProperties messageSourceProperties,
        Optional<MessageFilterProperties> messageFilterProperties);
}
