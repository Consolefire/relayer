package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.core.reader.OutboundMessageSource;
import com.consolefire.relayer.core.reader.SidelinedMessageSource;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.model.outbox.SidelinedMessage;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class CombinedMessageSourceReaderTask<ID extends Serializable,
        R extends MessageSourceReaderTaskResult<ID, OutboundMessage<ID>>>
        implements Callable<R> {

    protected final String sourceIdentifier;
    protected final OutboundMessageSource<ID> outboundMessageSource;
    protected final SidelinedMessageSource<ID> sidelinedMessageSource;

    public CombinedMessageSourceReaderTask(
            @NonNull String sourceIdentifier,
            @NonNull OutboundMessageSource<ID> outboundMessageSource,
            @NonNull SidelinedMessageSource<ID> sidelinedMessageSource) {
        this.sourceIdentifier = ifValid(sourceIdentifier);
        this.outboundMessageSource = ifValid(this.sourceIdentifier, outboundMessageSource);
        this.sidelinedMessageSource = ifValid(this.sourceIdentifier, sidelinedMessageSource);
    }

    private OutboundMessageSource<ID> ifValid(
            @NonNull String sourceIdentifier,
            @NonNull OutboundMessageSource<ID> outboundMessageSource) {
        if (!sourceIdentifier.equals(outboundMessageSource.getIdentifier())) {
            throw new IllegalArgumentException("Outbound message source identifier does not match the identifier.");
        }
        return null;
    }

    private SidelinedMessageSource<ID> ifValid(
            @NonNull String sourceIdentifier,
            @NonNull SidelinedMessageSource<ID> sidelinedMessageSource) {
        if (!sourceIdentifier.equals(sidelinedMessageSource.getIdentifier())) {
            throw new IllegalArgumentException("Sidelined message source identifier does not match the identifier.");
        }
        return null;
    }

    private String ifValid(@NonNull String sourceIdentifier) {
        if (sourceIdentifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Source identifier cannot be empty");
        }
        return sourceIdentifier;
    }

    @Override
    public R call() throws Exception {
        List<OutboundMessage<ID>> allMessageList = new ArrayList<>();
        Collection<OutboundMessage<ID>> outboundMessages = readOutboundMessages();
        if (null != outboundMessages) {
            allMessageList.addAll(outboundMessages);
        }

        Collection<SidelinedMessage<ID>> sidelinedMessages = readSidelinedMessages();
        if (null != sidelinedMessages) {
            sidelinedMessages.stream().map(SidelinedMessage::toOutboundMessage)
                    .forEach(allMessageList::add);
        }

        return buildResult(allMessageList);
    }

    private Collection<OutboundMessage<ID>> readOutboundMessages() {
        return null;
    }

    private Collection<SidelinedMessage<ID>> readSidelinedMessages() {
        return null;
    }

    private R buildResult(List<OutboundMessage<ID>> allMessageList) {
        return null;
    }


}
