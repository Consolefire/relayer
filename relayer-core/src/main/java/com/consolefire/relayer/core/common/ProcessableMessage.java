package com.consolefire.relayer.core.common;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;


@Getter
@ToString
public final class ProcessableMessage<ID extends Serializable, M extends Message<ID>> {

    @ToString.Exclude
    private final M message;
    @With
    private final int index;
    private final int total;
    private final String sourceIdentifier;
    private final UUID processorIdentifier;

    @Builder
    public ProcessableMessage(@NonNull M message, int index, int total, String sourceIdentifier,
        UUID processorIdentifier) {
        this.message = message;
        this.index = index;
        this.total = total;
        this.sourceIdentifier = sourceIdentifier;
        this.processorIdentifier = processorIdentifier;
    }

    @ToString.Include
    public ID getMessageId() {
        return Optional.ofNullable(message).map(M::getMessageId).orElse(null);
    }
}
