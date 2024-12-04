package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.checkpoint.task.CheckpointCompletedEvent;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexConsumer;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexProcessor;
import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointHandler implements AutoCloseable {

    private static final int DEFAULT_POOL_SIZE = 1;

    private final ExecutorService executorService;
    private final ReaderCheckpointService readerCheckpointService;
    private final ConcurrentHashMap<String, ReaderCheckpointIndexConsumer> checkpointIndexConsumers
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentConsumerQueue<CheckpointIndex>> checkpointIndexQueue
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentConsumerQueue<CheckpointCompletedEvent>> checkpointCompletedEventQueues;


    public ReaderCheckpointHandler(
        ReaderCheckpointService readerCheckpointService,
        ExecutorService executorService,
        Map<String, ConcurrentConsumerQueue<CheckpointCompletedEvent>> checkpointCompletedEventQueues) {
        this.executorService = executorService;
        this.readerCheckpointService = readerCheckpointService;
        this.checkpointCompletedEventQueues = new ConcurrentHashMap<>(checkpointCompletedEventQueues);
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }

    public void initialize(String sourceIdentifier) {
        if (checkpointIndexConsumers.containsKey(sourceIdentifier)) {
            log.warn("Reader checkpoint handler exists for source: {}", sourceIdentifier);
            checkpointIndexConsumers.get(sourceIdentifier).reset();
            return;
        }
        checkpointCompletedEventQueues.put(sourceIdentifier, new ConcurrentConsumerQueue<>());
        ConcurrentConsumerQueue<CheckpointIndex> indexQueue = new ConcurrentConsumerQueue<>();
        ReaderCheckpointIndexConsumer checkpointIndexConsumer = new ReaderCheckpointIndexConsumer(
            sourceIdentifier,
            indexQueue,
            new ReaderCheckpointIndexProcessor(sourceIdentifier, checkpointCompletedEventQueues.get(sourceIdentifier)));
        checkpointIndexConsumers.put(sourceIdentifier, checkpointIndexConsumer);
        checkpointIndexQueue.put(sourceIdentifier, indexQueue);
        executorService.submit(checkpointIndexConsumer);
    }

    public void process(String sourceIdentifier, CheckpointIndex checkpointIndex) {
        ConcurrentConsumerQueue<CheckpointIndex> indexQueue = checkpointIndexQueue.get(sourceIdentifier);
        if (null == indexQueue) {
            throw new RuntimeException("Handler not initialized");
        }
        indexQueue.add(checkpointIndex);
    }

    public void onSourceRegistered(String sourceIdentifier) {

    }

    public void onSourceDeregistered(String sourceIdentifier) {

    }

    public void onSourceCheckpointCompleted(String sourceIdentifier) {
        log.info("onSourceCheckpointCompleted: {}", sourceIdentifier);
        readerCheckpointService.complete(sourceIdentifier);
        checkpointIndexConsumers.get(sourceIdentifier).stop();
    }
}
