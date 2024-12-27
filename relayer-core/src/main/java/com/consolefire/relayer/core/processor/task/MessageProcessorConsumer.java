package com.consolefire.relayer.core.processor.task;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpointIndexMonitor;
import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.core.processor.MessageProcessor;
import com.consolefire.relayer.core.processor.MessageProcessorQueue;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageProcessorConsumer<ID extends Serializable, M extends Message<ID>> implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);
    @Getter
    private final String sourceIdentifier;
    private final MessageProcessor<ID, M> messageProcessor;
    private final MessageProcessorQueue<ID, M> messageProcessorQueue;
    private final ReaderCheckpointIndexMonitor readerCheckpointIndexMonitor;


    @Override
    public void run() {
        log.info("Starting consumer ...");
        consume();
    }

    public void stop() {
        if (messageProcessorQueue.isEmpty()) {
            log.debug("Queue is empty");
            running.set(false);
        }
        log.debug("Queue is not empty");
    }

    private void consume() {
        while (running.get()) {
            if (messageProcessorQueue.isEmpty()) {
                messageProcessorQueue.waitOnEmpty();
            }
            if (!running.get()) {
                break;
            }
            try {
                ProcessableMessage<ID, M> processableMessage = messageProcessorQueue.poll();
                if (null == processableMessage) {
                    log.info("Message is null ...");
                    continue;
                }
                messageProcessor.process(processableMessage);
            } catch (Exception exception) {
                log.error("Un-handled Error in message processing: {}", exception.getMessage(), exception);
                messageProcessorQueue.clear();
                readerCheckpointIndexMonitor.onSourceCheckpointCompleted(sourceIdentifier);
            }
        }
    }
}
