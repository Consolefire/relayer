package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointHandler implements Runnable {

    private final String sourceIdentifier;
    private final ConcurrentConsumerQueue<CheckpointIndex> checkpointIndexQueue;
    private final ReaderCheckpointService readerCheckpointService;

    public ReaderCheckpointHandler(String sourceIdentifier,
        ConcurrentConsumerQueue<CheckpointIndex> checkpointIndexQueue,
        ReaderCheckpointService readerCheckpointService) {
        this.sourceIdentifier = sourceIdentifier;
        this.checkpointIndexQueue = checkpointIndexQueue;
        this.readerCheckpointService = readerCheckpointService;
    }

    @Override
    public void run() {

    }

    public void reset() {

    }
}
