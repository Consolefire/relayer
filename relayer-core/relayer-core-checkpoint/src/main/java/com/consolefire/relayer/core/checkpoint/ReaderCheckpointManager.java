package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointManager {

    private final ExecutorService executorService;
    private final ReaderCheckpointService readerCheckpointService;
    private final ConcurrentHashMap<String, ReaderCheckpointHandler> readerCheckpointHandlers
        = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentConsumerQueue<CheckpointIndex>> checkpointIndexQueue
        = new ConcurrentHashMap<>();

    public ReaderCheckpointManager(ExecutorService executorService, ReaderCheckpointService readerCheckpointService) {
        this.executorService = executorService;
        this.readerCheckpointService = readerCheckpointService;
    }

    public void initialize(String sourceIdentifier) {
        if (readerCheckpointHandlers.containsKey(sourceIdentifier)) {
            log.warn("Reader checkpoint handler exists for source: {}", sourceIdentifier);
            readerCheckpointHandlers.get(sourceIdentifier).reset();
            return;
        }
        ConcurrentConsumerQueue<CheckpointIndex> indexQueue = new ConcurrentConsumerQueue<>();
        ReaderCheckpointHandler checkpointHandler = new ReaderCheckpointHandler(sourceIdentifier, indexQueue,
            readerCheckpointService);
        readerCheckpointHandlers.put(sourceIdentifier, checkpointHandler);
        executorService.submit(checkpointHandler);
    }


}
