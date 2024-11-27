package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.events.MessageStateEvent;
import com.consolefire.relayer.core.checkpoint.events.MessageStateEventQueue;
import com.consolefire.relayer.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MessageStateCheckpointHandler<ID extends Serializable> {

    private static volatile MessageStateCheckpointHandler INSTANCE;

    private ScheduledExecutorService scheduledExecutorService;
    private final Map<UUID, MessageStateCheckpoint<ID>> messageStateCheckpoints
            = new ConcurrentHashMap<>();
    private final Map<UUID, MessageStateEventQueue<ID>> messageStateEventQueues
            = new ConcurrentHashMap<>();
    private final Map<UUID, AtomicBoolean> initializedCheckpoints = new ConcurrentHashMap<>();


    private MessageStateCheckpointHandler() {
    }

    public static MessageStateCheckpointHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (MessageStateCheckpointHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageStateCheckpointHandler();
                }
            }
        }
        return INSTANCE;
    }

    public void initCheckpoint(UUID readerId, long readCount) {
        log.debug("Initializing checkpoint with [readerId: {}, readCount: {}]", readerId, readCount);
        if (initializedCheckpoints.containsKey(readerId)) {
            if (!initializedCheckpoints.get(readerId).get()) {
                messageStateCheckpoints.put(readerId, new MessageStateCheckpoint<>(readerId, readCount));
                initializedCheckpoints.put(readerId, new AtomicBoolean(true));
                initializedCheckpoints.get(readerId).set(true);
            }
            return;
        }
        messageStateCheckpoints.put(readerId, new MessageStateCheckpoint<>(readerId, readCount));
        initializedCheckpoints.put(readerId, new AtomicBoolean(true));
    }

    public void initCheckpoints(Set<Pair<UUID, Long>> readers) {
        if (null == readers || readers.isEmpty()) {
            return;
        }
        for (Pair<UUID, Long> pair : readers) {
            initCheckpoint(pair.left(), pair.right());
        }
    }


    public void on(MessageStateEvent<ID> event) {
        log.debug("Received event [{}]", event);
        if (null == event || null == event.messageId()) {
            log.warn("Received null event [{}]", event);
            return;
        }
        MessageStateCheckpoint<ID> messageStateCheckpoint = messageStateCheckpoints.get(event.readerId());
        if (null == messageStateCheckpoint) {
            log.warn("No message state checkpoint found for [readerId: {}]", event.readerId());
        }
        messageStateCheckpoint.updateProcessingState(event.messageId(), event.processingState());
    }

}
