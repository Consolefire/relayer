package com.consolefire.relayer.core.pipes;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;
import java.util.UUID;

public interface MessageQueue<ID extends Serializable, M extends Message<ID>> extends AutoCloseable {

    default UUID getIdentifier() {
        return UUID.randomUUID();
    }

    void configure();

    boolean enqueue(M message);

    M dequeue();

    long size();

}
