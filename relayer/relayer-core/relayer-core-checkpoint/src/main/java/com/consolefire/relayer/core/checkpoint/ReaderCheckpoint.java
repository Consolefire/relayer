package com.consolefire.relayer.core.checkpoint;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReaderCheckpoint extends Checkpoint<String> {

    private final Set<MessageSourceCheckpoint> messageSourceCheckpoints;
    private final Set<ProcessorCheckpoint> processorCheckpoints;

    @Builder
    public ReaderCheckpoint(String identifier,
                            Set<MessageSourceCheckpoint> messageSourceCheckpoints,
                            Set<ProcessorCheckpoint> processorCheckpoints) {
        super(identifier, null);
        this.messageSourceCheckpoints = Optional.ofNullable(messageSourceCheckpoints).orElse(Collections.emptySet());
        this.processorCheckpoints = Optional.ofNullable(processorCheckpoints).orElse(Collections.emptySet());
    }

    public Set<ProcessorCheckpoint> getProcessorCheckpoints() {
        return Collections.unmodifiableSet(processorCheckpoints);
    }

    public Set<MessageSourceCheckpoint> getMessageSourceCheckpoints() {
        return Collections.unmodifiableSet(messageSourceCheckpoints);
    }

    public long sumOfProcessedCount() {
        return processorCheckpoints
                .stream()
                .mapToLong(ProcessorCheckpoint::getProcessedCount)
                .sum();

    }
}
