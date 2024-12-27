package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.checkpoint.task.CheckpointCompletedEvent;
import com.consolefire.relayer.core.checkpoint.task.CheckpointIndex;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexConsumer;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointIndexHandler;
import com.consolefire.relayer.util.ConsumerQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointProcessor {

    private final ExecutorService executorService;
    private final ReaderCheckpointService readerCheckpointService;
    private final ConcurrentHashMap<String, ReaderCheckpointIndexConsumer> checkpointIndexConsumers
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConsumerQueue<CheckpointIndex>> checkpointIndexQueue
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConsumerQueue<CheckpointCompletedEvent>> checkpointCompletedEventQueues = new ConcurrentHashMap<>();

    public ReaderCheckpointProcessor(ExecutorService executorService, ReaderCheckpointService readerCheckpointService) {
        this.executorService = executorService;
        this.readerCheckpointService = readerCheckpointService;
    }


    public void initialize(String sourceIdentifier) {
        if (checkpointIndexConsumers.containsKey(sourceIdentifier)) {
            log.warn("Reader checkpoint handler exists for source: {}", sourceIdentifier);
            checkpointIndexConsumers.get(sourceIdentifier).reset();
            return;
        }
        checkpointCompletedEventQueues.put(sourceIdentifier, new ConsumerQueue<>());
        ConsumerQueue<CheckpointIndex> indexQueue = new ConsumerQueue<>();
        ReaderCheckpointIndexConsumer checkpointIndexConsumer = new ReaderCheckpointIndexConsumer(
            sourceIdentifier,
            indexQueue,
            new ReaderCheckpointIndexHandler(sourceIdentifier, checkpointCompletedEventQueues.get(sourceIdentifier)));
        checkpointIndexConsumers.put(sourceIdentifier, checkpointIndexConsumer);
        checkpointIndexQueue.put(sourceIdentifier, indexQueue);
        executorService.submit(checkpointIndexConsumer);
    }

    public void processIndex(String sourceIdentifier, CheckpointIndex checkpointIndex) {
        ConsumerQueue<CheckpointIndex> indexQueue = checkpointIndexQueue.get(sourceIdentifier);
        if (null == indexQueue) {
            initialize(sourceIdentifier);
        }
        checkpointIndexQueue.get(sourceIdentifier).add(checkpointIndex);
    }


}
