package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.core.checkpoint.MessageSourceCheckpoint;
import com.consolefire.relayer.core.reader.OutboundMessageSource;
import com.consolefire.relayer.model.OutboundMessage;
import lombok.Builder;

import java.io.Serializable;
import java.util.Collection;

public class OutboundMessageSourceReaderTask<ID extends Serializable>
        extends MessageSourceReaderTask<
        ID, OutboundMessage<ID>, OutboundMessageSource<ID>,
        OutboundMessageSourceReaderTaskResult<ID>
        > {

    @Builder
    public OutboundMessageSourceReaderTask(
            OutboundMessageSource<ID> messageSource,
            MessageSourceCheckpoint messageSourceCheckpoint) {
        super(messageSource, messageSourceCheckpoint);
    }

    @Override
    protected OutboundMessageSourceReaderTaskResult<ID> buildResult(
            Collection<OutboundMessage<ID>> messages) {
        return OutboundMessageSourceReaderTaskResult.<ID>builder()
                .sourceIdentifier(messageSource.getIdentifier())
                .messages(messages)
                .build();
    }
}
