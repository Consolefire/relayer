package com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.common.MessageProcessingState;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.consolefire.relayer.core.common.MessageProcessingState.*;
import static java.util.Optional.ofNullable;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProcessorCheckpoint<MESSAGE_ID extends Serializable> extends Checkpoint<UUID> {

    private static final Set<MessageProcessingState> COMPLETED_STATES
            = Set.of(COMPLETED, FAILED, SENT, MOVED, DELETED);

    @EqualsAndHashCode.Include
    private final String referenceCheckpointId;
    private final ConcurrentHashMap<MESSAGE_ID, MessageProcessingState> messageProcessingStateMap;

    @Builder
    public ProcessorCheckpoint(
            @NonNull UUID identifier,
            @NonNull String referenceCheckpointId,
            ConcurrentHashMap<MESSAGE_ID, MessageProcessingState> messageProcessingStateMap) {
        super(identifier, referenceCheckpointId);
        this.referenceCheckpointId = referenceCheckpointId;
        this.messageProcessingStateMap = ofNullable(messageProcessingStateMap).orElse(new ConcurrentHashMap<>());
    }

    public void updateMessageProcessingState(MESSAGE_ID messageId, MessageProcessingState state) {
        if (messageProcessingStateMap.containsKey(messageId)) {
            messageProcessingStateMap.put(messageId, state);
        } else {
            messageProcessingStateMap.put(messageId, state);
        }
    }

    public long getProcessedCount() {
        return messageProcessingStateMap.values().stream()
                .filter(COMPLETED_STATES::contains)
                .count();
    }
}
