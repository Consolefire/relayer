package com.consolefire.relayer.core.distributor;

import com.consolefire.relayer.core.checkpoint.events.MessageProcessStateEventPublisher;
import com.consolefire.relayer.core.checkpoint.events.MessageStateEvent;
import com.consolefire.relayer.core.common.MessageProcessingState;
import com.consolefire.relayer.core.common.MessageWrapper;
import com.consolefire.relayer.model.Message;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractMessageDistributor<
        ID extends Serializable, M extends Message<ID>, W extends MessageWrapper<ID, M>>
        implements MessageDistributor<ID, M, W> {

    private final Set<UUID> processorIdentifiers;
    private final MessageProcessStateEventPublisher<ID> messageProcessStateEventPublisher;


    @Override
    public UUID distribute(W messageWrapper) {
        UUID targetProcessorId = identifyProcessor(messageWrapper);

        messageProcessStateEventPublisher.send(new MessageStateEvent<>(messageWrapper.readerIdentifier(), messageWrapper.getMessageId(), MessageProcessingState.IN_PROCESS));
        return targetProcessorId;
    }

    protected abstract UUID identifyProcessor(W messageWrapper);
}
