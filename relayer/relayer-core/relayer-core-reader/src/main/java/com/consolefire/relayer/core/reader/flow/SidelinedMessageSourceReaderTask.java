package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.core.checkpoint.MessageSourceCheckpoint;
import com.consolefire.relayer.core.reader.SidelinedMessageSource;
import com.consolefire.relayer.model.SidelinedMessage;
import lombok.Builder;

import java.io.Serializable;
import java.util.Collection;

public class SidelinedMessageSourceReaderTask<ID extends Serializable>
        extends MessageSourceReaderTask<
        ID, SidelinedMessage<ID>, SidelinedMessageSource<ID>,
        SidelinedMessageSourceReaderTaskResult<ID>
        > {

    @Builder
    public SidelinedMessageSourceReaderTask(SidelinedMessageSource<ID> messageSource, MessageSourceCheckpoint messageSourceCheckpoint) {
        super(messageSource, messageSourceCheckpoint);
    }

    @Override
    protected SidelinedMessageSourceReaderTaskResult<ID> buildResult(Collection<SidelinedMessage<ID>> messages) {
        return SidelinedMessageSourceReaderTaskResult.<ID>builder()
                .sourceIdentifier(messageSource.getIdentifier())
                .messages(messages)
                .build();
    }


}
