package com.consolefire.relayer.core.checkpoint.events;

import java.io.Serializable;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class MessageStateEventPoller<ID extends Serializable> implements Callable<MessageStateEvent<ID>> {

    private final UUID readerId;
    private final ConcurrentLinkedQueue<MessageStateEvent<ID>> messageStateEventQueue;
    private final long timeout;
    private final TimeUnit timeoutUnit;

    public MessageStateEventPoller(
            UUID readerId, ConcurrentLinkedQueue<MessageStateEvent<ID>> messageStateEventQueue) {
        this.readerId = readerId;
        this.messageStateEventQueue = messageStateEventQueue;
        this.timeout = 0;
        this.timeoutUnit = TimeUnit.MILLISECONDS;
    }

    public MessageStateEventPoller(
            UUID readerId, ConcurrentLinkedQueue<MessageStateEvent<ID>> messageStateEventQueue, long timeout, TimeUnit timeoutUnit) {
        this.readerId = readerId;
        this.messageStateEventQueue = messageStateEventQueue;
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    @Override
    public MessageStateEvent<ID> call() throws Exception {
        return messageStateEventQueue.poll();
    }
}
