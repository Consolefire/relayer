package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.model.outbox.SidelinedMessage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class SidelinedMessageSourceReaderTaskResult<ID extends Serializable>
        extends MessageSourceReaderTaskResult<ID, SidelinedMessage<ID>> {

    @Builder
    public SidelinedMessageSourceReaderTaskResult(
            @NonNull String sourceIdentifier,
            Collection<SidelinedMessage<ID>> messages) {
        super(sourceIdentifier, messages);
    }
}
