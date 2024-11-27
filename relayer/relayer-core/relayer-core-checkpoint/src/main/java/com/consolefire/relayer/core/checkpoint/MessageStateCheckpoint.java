package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.common.MessageProcessingState;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.consolefire.relayer.core.common.MessageProcessingState.*;

public class MessageStateCheckpoint<ID extends Serializable> {

    private static final Set<MessageProcessingState> COMPLETED_STATES
            = Set.of(COMPLETED, FAILED, SENT, MOVED, DELETED);

    @Getter
    private final UUID readerIdentifier;
    @Getter
    private final long readCount;
    private final ConcurrentHashMap<ID, MessageProcessingState> processingStates = new ConcurrentHashMap<>();


    public MessageStateCheckpoint(
            @NonNull UUID readerIdentifier, long readCount) {
        this.readerIdentifier = readerIdentifier;
        this.readCount = readCount;
    }

    public long getProcessedCount() {
        return processingStates.values().stream()
                .filter(COMPLETED_STATES::contains)
                .count();
    }

    public Map<ID, MessageProcessingState> getProcessingStates() {
        return new HashMap<>(processingStates);
    }

    public void updateProcessingState(@NonNull ID messageId, @NonNull MessageProcessingState state) {
        processingStates.put(messageId, state);
    }

    public void clearProcessingStates() {
        processingStates.clear();
    }
}
