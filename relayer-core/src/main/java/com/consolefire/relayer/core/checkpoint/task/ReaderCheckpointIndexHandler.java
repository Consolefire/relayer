package com.consolefire.relayer.core.checkpoint.task;

import com.consolefire.relayer.util.ConsumerQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderCheckpointIndexHandler {

    private static final int INVALID_COUNT = -1;

    private final Object lock = new Object();
    private final AtomicInteger total = new AtomicInteger(INVALID_COUNT);
    private final AtomicBoolean countDownLatchInitialized = new AtomicBoolean(false);
    private final AtomicReference<ConcurrentHashMap<Integer, Boolean>> indexMapReference = new AtomicReference<>();

    private final String sourceIdentifier;
    private final ConsumerQueue<CheckpointCompletedEvent> consumerQueue;

    private volatile CountDownLatch countDownLatch;


    public ReaderCheckpointIndexHandler(
        String sourceIdentifier,
        ConsumerQueue<CheckpointCompletedEvent> consumerQueue) {
        this.sourceIdentifier = sourceIdentifier;
        this.consumerQueue = consumerQueue;
        this.indexMapReference.set(new ConcurrentHashMap<>());
    }

    public void processIndex(CheckpointIndex index) {
        log.debug("Processing index: {}", index);
        total.compareAndSet(INVALID_COUNT, index.total());
        if (!countDownLatchInitialized.get()) {
            countDownLatch = new CountDownLatch(index.total());
            countDownLatch.countDown();
            countDownLatchInitialized.set(true);
        } else {
            countDownLatch.countDown();
        }
        log.debug("countDownLatch count: {}", countDownLatch.getCount());
        indexMapReference.get().putIfAbsent(index.index(), true);
        if (completed()) {
            log.debug("Checkpoint completed for source: {}", sourceIdentifier);
            consumerQueue.add(new CheckpointCompletedEvent(sourceIdentifier, true));
        } else {
            log.debug("Checkpoint not completed for source: {}", sourceIdentifier);
        }
    }

    private boolean completed() {
        log.debug("Checking completeness: {}", sourceIdentifier);
        if (INVALID_COUNT == total.get()) {
            log.debug("{} >> total is invalid", sourceIdentifier);
            if (null == countDownLatch) {
                log.debug("{} >> null countdownlatch", sourceIdentifier);
                return true;
            }
            log.debug("{} >> total is invalid, return false", sourceIdentifier);
            return false;
        }
        boolean b = 0 == countDownLatch.getCount();
        log.debug("{} >> countDownLatch is 0", b);
        return b;
    }


}
