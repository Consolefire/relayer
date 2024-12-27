package com.consolefire.relayer.core.distributor;

import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.core.processor.MessageProcessorQueue;
import com.consolefire.relayer.core.processor.MessageProcessorQueueResolver;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageDistributor<ID extends Serializable, M extends Message<ID>>
    implements MessageDistributor<ID, M> {

    private static final int MIN_INDEX = 0;

    private final MessageProcessorQueueResolver messageProcessorQueueResolver;

    private void distribute(M message, final int index, final int total) {
        MessageProcessorQueue<ID, M> targetProcessorQueue = messageProcessorQueueResolver
            .resolveQueue(message.getGroupId());
        if (null == targetProcessorQueue) {
            log.error("No processor resolved for group: {}", message.getGroupId());
            throw new UndefinedProcessorException();
        }
        targetProcessorQueue.add(ProcessableMessage.<ID, M>builder()
            .message(message)
            .index(index)
            .total(total)
            .build());
    }

    @Override
    public void distribute(Collection<M> messages) {
        if (null == messages || messages.isEmpty()) {
            log.info("No messages to distribute");
            return;
        }
        var total = messages.size();
        log.info("Distributing total [{}] messages.", total);
        AtomicInteger counter = new AtomicInteger(MIN_INDEX);
        for (M message : messages) {
            int index = counter.incrementAndGet();
            distribute(message, index, total);
        }
    }

}
