package com.consolefire.relayer.core.distributor;

import com.consolefire.relayer.core.common.MessageWrapper;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractMessageDistributor<
        ID extends Serializable, M extends Message<ID>, W extends MessageWrapper<ID, M>>
        implements MessageDistributor<ID, M, W> {

    private final Set<UUID> processorIdentifiers;


    @Override
    public UUID distribute(W messageWrapper) {
        UUID targetProcessorId = identifyProcessor(messageWrapper);

        return targetProcessorId;
    }

    protected abstract UUID identifyProcessor(W messageWrapper);
}
