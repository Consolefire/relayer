package com.consolefire.relayer.core.checkpoint.task;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpointIndexMonitor;
import com.consolefire.relayer.util.ConsumerQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointCompletedConsumer implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final String sourceIdentifier;
    private final ReaderCheckpointIndexMonitor readerCheckpointIndexMonitor;
    private final ConsumerQueue<CheckpointCompletedEvent> checkpointCompletedEventQueue;

    public ReaderCheckpointCompletedConsumer(String sourceIdentifier, ReaderCheckpointIndexMonitor readerCheckpointIndexMonitor,
        ConsumerQueue<CheckpointCompletedEvent> checkpointCompletedEventQueue) {
        this.sourceIdentifier = sourceIdentifier;
        this.readerCheckpointIndexMonitor = readerCheckpointIndexMonitor;
        this.checkpointCompletedEventQueue = checkpointCompletedEventQueue;
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        consume();
    }

    private void consume() {
        log.debug("***-- Consuming CheckpointCompletedEvent");
        while (running.get()) {
            try {
                if (checkpointCompletedEventQueue.isEmpty()) {
                    log.debug("***-- waiting for CheckpointCompletedEvent");
                    checkpointCompletedEventQueue.waitOnEmpty();
                }
                log.debug("***-- polling for the next CheckpointCompletedEvent");
                CheckpointCompletedEvent event = checkpointCompletedEventQueue.poll();
                if (event != null) {
                    log.debug("CheckpointCompletedEvent: {}", event);
                    if (!sourceIdentifier.equals(event.sourceIdentifier())) {
                        log.info("Invalid event..");
                    } else {
                        onEvent(event);
                    }
                } else {
                    log.debug("Checkpoint event is null...");
                }
                if (!running.get()) {
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public boolean isRunning() {
        return running.get();
    }

    private void onEvent(CheckpointCompletedEvent event) {
        log.info("Processing CheckpointCompletedEvent: {}", event);
        readerCheckpointIndexMonitor.onSourceCheckpointCompleted(event.sourceIdentifier());
    }
}
