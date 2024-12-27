package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.checkpoint.task.CheckpointCompletedEvent;
import com.consolefire.relayer.core.checkpoint.task.CheckpointIndex;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexConsumer;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexHandler;
import com.consolefire.relayer.core.msgsrc.MessageSourceRegisteredEvent;
import com.consolefire.relayer.core.msgsrc.MessageSourceRegisteredEventListener;
import com.consolefire.relayer.core.msgsrc.MessageSourceUnregisteredEventListener;
import com.consolefire.relayer.util.ConsumerQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointIndexMonitor
    implements MessageSourceRegisteredEventListener, MessageSourceUnregisteredEventListener {

    private final ExecutorService executorService;
    private final ReaderCheckpointService readerCheckpointService;
    private final ConcurrentHashMap<String, ReaderCheckpointIndexConsumer> checkpointIndexConsumers
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConsumerQueue<CheckpointIndex>> checkpointIndexQueue
        = new ConcurrentHashMap<>();

    public ReaderCheckpointIndexMonitor(
        ReaderCheckpointService readerCheckpointService,
        ExecutorService executorService) {
        this.executorService = executorService;
        this.readerCheckpointService = readerCheckpointService;
    }


    public void initialize(String sourceIdentifier) {
        if (checkpointIndexConsumers.containsKey(sourceIdentifier)) {
            log.warn("Reader checkpoint handler exists for source: {}", sourceIdentifier);
            checkpointIndexConsumers.get(sourceIdentifier).reset();
            return;
        }
        ConsumerQueue<CheckpointIndex> indexQueue = new ConsumerQueue<>();
        ReaderCheckpointIndexConsumer checkpointIndexConsumer = new ReaderCheckpointIndexConsumer(
            sourceIdentifier,
            indexQueue,
            new ReaderCheckpointIndexHandler(sourceIdentifier, checkpointCompletedEventQueues.get(sourceIdentifier)));
        checkpointIndexConsumers.put(sourceIdentifier, checkpointIndexConsumer);
        checkpointIndexQueue.put(sourceIdentifier, indexQueue);
        executorService.submit(checkpointIndexConsumer);
    }

    public void process(String sourceIdentifier, CheckpointIndex checkpointIndex) {
        ConsumerQueue<CheckpointIndex> indexQueue = checkpointIndexQueue.get(sourceIdentifier);
        if (null == indexQueue) {
            initialize(sourceIdentifier);
        }
        checkpointIndexQueue.get(sourceIdentifier).add(checkpointIndex);
    }


    public void onSourceCheckpointCompleted(String sourceIdentifier) {
        log.info("onSourceCheckpointCompleted: {}", sourceIdentifier);
        readerCheckpointService.complete(sourceIdentifier);
    }

    @Override
    public void onMessageSourceRegistered(MessageSourceRegisteredEvent event) {
        log.info("Handling message source registration: {}", event);
    }

    @Override
    public void onMessageSourceUnregistered(String messageSource) {
        log.info("Handling message source un-registration: {}", messageSource);
    }
}
