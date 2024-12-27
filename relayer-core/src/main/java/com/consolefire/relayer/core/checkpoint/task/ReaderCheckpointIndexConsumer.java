package com.consolefire.relayer.core.checkpoint.task;

import com.consolefire.relayer.util.ConsumerQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointIndexConsumer implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Getter
    private final String sourceIdentifier;
    private final ConsumerQueue<CheckpointIndex> checkpointIndexQueue;
    private final ReaderCheckpointIndexHandler readerCheckpointIndexHandler;

    public ReaderCheckpointIndexConsumer(
        String sourceIdentifier,
        ConsumerQueue<CheckpointIndex> checkpointIndexQueue,
        ReaderCheckpointIndexHandler readerCheckpointIndexHandler) {
        this.sourceIdentifier = sourceIdentifier;
        this.checkpointIndexQueue = checkpointIndexQueue;
        this.readerCheckpointIndexHandler = readerCheckpointIndexHandler;
    }

    public synchronized void stop() {
        log.debug("Stopping consumer");
        this.running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        consumeIndex();
    }

    private void consumeIndex() {
        while (running.get()) {
            log.debug("Consuming checkpoint index for source: {}", sourceIdentifier);
            try {
                if (checkpointIndexQueue.isEmpty()) {
                    checkpointIndexQueue.waitOnEmpty();
                }

                CheckpointIndex index = checkpointIndexQueue.poll();
                if (index != null) {
                    readerCheckpointIndexHandler.processIndex(index);
                } else {
                    log.debug("Checkpoint index is null...");
                }
                if (!running.get()) {
                    return;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("Consumer stopped");
    }


    public void reset() {
        log.debug("Resetting consumer");
        synchronized (this) {
            checkpointIndexQueue.clear();
        }
    }

}
