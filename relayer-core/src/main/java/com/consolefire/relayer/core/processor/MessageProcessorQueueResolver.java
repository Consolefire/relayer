package com.consolefire.relayer.core.processor;

import com.consolefire.relayer.model.Message;
import com.google.common.hash.Hashing;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import org.apache.commons.lang3.SerializationUtils;

public class MessageProcessorQueueResolver<ID extends Serializable, M extends Message<ID>> {

    private final int processorCount;
    private final ConcurrentHashMap<Integer, UUID> processorIdentifierMap
        = new ConcurrentHashMap<>();

    public MessageProcessorQueueResolver(
        int processorCount, @NonNull Set<UUID> processorIdentifiers) {
        this.processorCount = processorCount;
        if (null != processorIdentifiers && !processorIdentifiers.isEmpty()) {

        }
    }

    public MessageProcessorQueue<ID, M> resolveQueue(String groupId) {
        if (null == groupId) {

        }
        int hash = Hashing.consistentHash(
            Hashing.murmur3_128().newHasher()
                .putBytes(SerializationUtils.serialize(groupId))
                .hash()
                .asLong(),
            processorCount);

        return null;
    }

}
