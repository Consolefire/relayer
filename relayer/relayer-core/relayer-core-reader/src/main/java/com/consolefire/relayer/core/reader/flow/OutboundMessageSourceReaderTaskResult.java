package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.model.OutboundMessage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class OutboundMessageSourceReaderTaskResult<ID extends Serializable>
        extends MessageSourceReaderTaskResult<ID, OutboundMessage<ID>> {

    @Builder
    public OutboundMessageSourceReaderTaskResult(
            @NonNull String sourceIdentifier,
            Collection<OutboundMessage<ID>> messages) {
        super(sourceIdentifier, messages);
    }
}
