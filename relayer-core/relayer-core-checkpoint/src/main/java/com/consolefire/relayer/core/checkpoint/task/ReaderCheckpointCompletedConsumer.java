package com.consolefire.relayer.core.checkpoint.task;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpointHandler;
import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointCompletedConsumer implements Runnable {

    private final String sourceIdentifier;
    private final ReaderCheckpointHandler readerCheckpointHandler;
    private final ConcurrentConsumerQueue<CheckpointCompletedEvent> checkpointCompletedEventQueue;

    public ReaderCheckpointCompletedConsumer(String sourceIdentifier, ReaderCheckpointHandler readerCheckpointHandler,
        ConcurrentConsumerQueue<CheckpointCompletedEvent> checkpointCompletedEventQueue) {
        this.sourceIdentifier = sourceIdentifier;
        this.readerCheckpointHandler = readerCheckpointHandler;
        this.checkpointCompletedEventQueue = checkpointCompletedEventQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (checkpointCompletedEventQueue.isEmpty()) {
                    log.debug("***-- waiting for CheckpointCompletedEvent");
                    checkpointCompletedEventQueue.waitOnEmpty();
                }

                CheckpointCompletedEvent event = checkpointCompletedEventQueue.poll();
                if (event != null) {
                    log.debug("CheckpointCompletedEvent: {}", event);
                    if (!sourceIdentifier.equals(event.sourceIdentifier())) {
                        log.info("Invalid event..");
                    } else {
                        process(event);
                    }
                } else {
                    log.debug("Checkpoint event is null...");
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void process(CheckpointCompletedEvent event) {
        readerCheckpointHandler.onSourceCheckpointCompleted(event.sourceIdentifier());
    }
}
