package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.ParkedMessage;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface ParkedMessageReader<ID extends Serializable, M extends Message<ID>, PM extends ParkedMessage<ID, M>> {

    default Collection<PM> read(MessageSourceProperties messageSourceProperties) {
        return read(messageSourceProperties, Optional.of(MessageFilterProperties.DEFAULT));
    }

    Collection<PM> read(MessageSourceProperties messageSourceProperties,
        Optional<MessageFilterProperties> messageFilterProperties);
}
