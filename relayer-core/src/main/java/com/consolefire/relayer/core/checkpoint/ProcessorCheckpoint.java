package com.consolefire.relayer.core.checkpoint;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
public class ProcessorCheckpoint {

    private final String processorIdentifier;
    private final String sourceIdentifier;
    @With
    private final Long index;
    private final Long total;
    @With
    private final boolean completed;

    @Builder
    public ProcessorCheckpoint(
        @NonNull String processorIdentifier,
        @NonNull String sourceIdentifier,
        @NonNull Long index,
        @NonNull Long total,
        @NonNull Boolean completed) {
        this.processorIdentifier = processorIdentifier;
        this.sourceIdentifier = sourceIdentifier;
        this.index = index;
        this.total = total;
        this.completed = completed;
    }
}
