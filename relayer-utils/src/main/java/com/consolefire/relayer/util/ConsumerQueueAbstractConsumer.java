package com.consolefire.relayer.util;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConsumerQueueAbstractConsumer<E> implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ConsumerQueue<E> consumerQueue;

    public <Q extends ConsumerQueue<E>> ConsumerQueueAbstractConsumer(Q consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    public synchronized void stop() {
        log.debug("Stopping consumer");
        this.running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        consume();
    }

    private void consume() {
        while (running.get()) {
            try {
                if (consumerQueue.isEmpty()) {
                    consumerQueue.waitOnEmpty();
                }
                log.debug("Polling from queue...");
                E element = consumerQueue.poll();
                if (element != null) {
                    processElement(element);
                } else {
                    log.debug("Element is null...");
                }
                if (!running.get()) {
                    log.info("Consumer stopped");
                    return;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("Consumer stopped");
    }

    protected abstract void processElement(E element);

}
