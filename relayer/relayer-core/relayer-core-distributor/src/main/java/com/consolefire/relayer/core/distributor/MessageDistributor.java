package com.consolefire.relayer.core.distributor;

import com.consolefire.relayer.core.common.MessageWrapper;
import com.consolefire.relayer.model.Message;

import java.io.Serializable;
import java.util.UUID;

/**
 * Message Distributor to distribute messages to the respective processors.
 *
 * @param <ID>
 * @param <M>
 */
public interface MessageDistributor
        <ID extends Serializable, M extends Message<ID>, W extends MessageWrapper<ID, M>> {

    /**
     * Distributes a Message.
     *
     * @param messageWrapper
     * @return Processor ID
     */
    UUID distribute(W messageWrapper);

}
