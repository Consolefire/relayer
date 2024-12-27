package com.consolefire.relayer.core.checkpoint.task;

import static java.util.Optional.ofNullable;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpointIndexMonitor;
import com.consolefire.relayer.core.msgsrc.MessageSourceRegisteredEvent;
import com.consolefire.relayer.core.msgsrc.MessageSourceRegisteredEventListener;
import com.consolefire.relayer.core.msgsrc.MessageSourceUnregisteredEventListener;
import com.consolefire.relayer.util.ConsumerQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointStateMonitor
    implements MessageSourceRegisteredEventListener, MessageSourceUnregisteredEventListener {

    private final ExecutorService executorService;
    private final ReaderCheckpointIndexMonitor readerCheckpointIndexMonitor;
    private final Map<String, ConsumerQueue<CheckpointCompletedEvent>> checkpointConsumerQueues;
    private final Map<String, ReaderCheckpointCompletedConsumer> readerCheckpointCompletedConsumers;

    public ReaderCheckpointStateMonitor(
        @NonNull ExecutorService executorService,
        @NonNull ReaderCheckpointIndexMonitor readerCheckpointIndexMonitor,
        Map<String, ConsumerQueue<CheckpointCompletedEvent>> checkpointConsumerQueues,
        Map<String, ReaderCheckpointCompletedConsumer> readerCheckpointCompletedConsumers) {
        this.executorService = executorService;
        this.readerCheckpointIndexMonitor = readerCheckpointIndexMonitor;
        this.checkpointConsumerQueues = ofNullable(checkpointConsumerQueues).orElse(new ConcurrentHashMap<>());
        this.readerCheckpointCompletedConsumers = ofNullable(readerCheckpointCompletedConsumers).orElse(
            new ConcurrentHashMap<>());
    }


    public void start(String sourceIdentifier) {
        log.info("Starting reader checkpoint state monitor for source: {}", sourceIdentifier);
        ReaderCheckpointCompletedConsumer consumer = readerCheckpointCompletedConsumers.get(sourceIdentifier);
        if (null == consumer) {
            log.info("Creating reader checkpoint state consumer for source: {}", sourceIdentifier);
            ConsumerQueue<CheckpointCompletedEvent> queue = new ConsumerQueue<>();
            consumer = new ReaderCheckpointCompletedConsumer(sourceIdentifier, readerCheckpointIndexMonitor, queue);
            checkpointConsumerQueues.put(sourceIdentifier, queue);
            readerCheckpointCompletedConsumers.put(sourceIdentifier, consumer);
        } else if (consumer.isRunning()) {
            log.info("Reader checkpoint state consumer for source: {} is running", sourceIdentifier);
            return;
        }
        log.info("Starting reader checkpoint state consumer for source: {}", sourceIdentifier);
        executorService.submit(consumer);
    }

    public void stop(String sourceIdentifier) {
        log.info("Stopping reader checkpoint state monitor for source: {}", sourceIdentifier);
        ReaderCheckpointCompletedConsumer consumer = readerCheckpointCompletedConsumers.get(sourceIdentifier);
        if (null != consumer && consumer.isRunning()) {
            consumer.stop();
        }
    }

    @Override
    public void onMessageSourceRegistered(MessageSourceRegisteredEvent event) {
        log.info("Handling message source registration: {}", event);
        if (null != event && null != event.sourceIdentifier()) {
            start(event.sourceIdentifier());
        }
    }

    @Override
    public void onMessageSourceUnregistered(String messageSource) {
        log.info("Handling message source un-registration: {}", messageSource);
        if (null != messageSource) {
            stop(messageSource);
        }
    }
}
