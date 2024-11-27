package com.consolefire.relayer.core.checkpoint;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import static java.lang.String.format;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class MessageSourceCheckpoint extends Checkpoint<String> {

    public static final String INBOUND = "inbound";
    public static final String OUTBOUND = "outbound";
    public static final String SIDELINED = "sidelined";
    public static final String DEAD_LETTER = "dead_letter";

    private final int readCount;

    @Builder
    public MessageSourceCheckpoint(@NonNull String identifier, @NonNull String referenceCheckpointId, int readCount) {
        super(identifier, referenceCheckpointId);
        this.readCount = readCount;
    }

    public MessageSourceCheckpoint withReadCount(int readCount) {
        return MessageSourceCheckpoint.builder()
                .identifier(identifier)
                .referenceCheckpointId(referenceCheckpointId)
                .readCount(readCount)
                .build();
    }

    public static MessageSourceCheckpoint forInboundMessage(
            @NonNull String identifier, int readCount, @NonNull String checkpointReference) {
        return new MessageSourceCheckpoint(format("%::%", INBOUND, identifier), checkpointReference, readCount);
    }

    public static MessageSourceCheckpoint forOutboundMessage(
            @NonNull String identifier, int readCount, @NonNull String checkpointReference) {
        return new MessageSourceCheckpoint(format("%::%", OUTBOUND, identifier), checkpointReference, readCount);
    }

    public static MessageSourceCheckpoint forSidelinedMessage(
            @NonNull String identifier, int readCount, @NonNull String checkpointReference) {
        return new MessageSourceCheckpoint(format("%::%", SIDELINED, identifier), checkpointReference, readCount);
    }

    public static MessageSourceCheckpoint forDeadLetterMessage(
            @NonNull String identifier, int readCount, @NonNull String checkpointReference) {
        return new MessageSourceCheckpoint(format("%::%", DEAD_LETTER, identifier), checkpointReference, readCount);
    }
}
