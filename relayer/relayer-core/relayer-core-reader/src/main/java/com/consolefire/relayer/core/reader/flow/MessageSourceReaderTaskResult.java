package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.model.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MessageSourceReaderTaskResult<ID extends Serializable, M extends Message<ID>> {

    @EqualsAndHashCode.Include
    protected final String sourceIdentifier;
    protected final Collection<M> messages;

    public MessageSourceReaderTaskResult(String sourceIdentifier, Collection<M> messages) {
        this.sourceIdentifier = sourceIdentifier;
        this.messages = messages;
    }
}
