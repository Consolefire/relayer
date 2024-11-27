package com.consolefire.relayer.core.checkpoint.events;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class MessageProcessStateEventPublisher<ID extends Serializable> {

    private final Map<UUID, ConcurrentLinkedQueue<MessageStateEvent<ID>>> messageStateEventQueues;

    public void send(MessageStateEvent<ID> event) {
        messageStateEventQueues.get(event.readerId()).offer(event);
    }

}
