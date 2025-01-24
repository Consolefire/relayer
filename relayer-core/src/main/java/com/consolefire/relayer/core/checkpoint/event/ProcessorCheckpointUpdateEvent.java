package com.consolefire.relayer.core.checkpoint.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class ProcessorCheckpointUpdateEvent {

    private final String processorIdentifier;
    private final String sourceIdentifier;
    private final Long index;
    private final Long total;
    private final boolean completed;

}
