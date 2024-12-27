package com.consolefire.relayer.core.distributor;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Message Distributor to distribute messages to the respective processors.
 *
 * @param <ID>
 * @param <M>
 */
public interface MessageDistributor
    <ID extends Serializable, M extends Message<ID>> {

    /**
     * Distributes a Message.
     *
     * @param messages
     * @return Processor ID
     */
    void distribute(Collection<M> messages);
}
