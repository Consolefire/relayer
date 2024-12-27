package com.consolefire.relayer.core.common;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;


@Getter
@ToString(onlyExplicitlyIncluded = true)
public final class ProcessableMessage<ID extends Serializable, M extends Message<ID>> {

    @ToString.Exclude
    private final M message;
    @With
    @ToString.Include
    private final int index;
    @ToString.Include
    private final int total;
    @ToString.Include
    private final String sourceIdentifier;

    @Builder
    public ProcessableMessage(@NonNull M message, int index, int total, String sourceIdentifier) {
        this.message = message;
        this.index = index;
        this.total = total;
        this.sourceIdentifier = sourceIdentifier;
    }

    @ToString.Include
    public ID getMessageId() {
        return Optional.ofNullable(message).map(M::getMessageId).orElse(null);
    }
}
