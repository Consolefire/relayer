package com.consolefire.relayer.core.checkpoint.repository.impl;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import com.consolefire.relayer.core.checkpoint.event.ProcessorCheckpointUpdateEventPublisher;
import com.consolefire.relayer.core.checkpoint.store.InMemoryProcessorCheckpointStore;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import lombok.NonNull;

public class InMemoryProcessorCheckpointRepository extends AbstractProcessorCheckpointRepository {

    public InMemoryProcessorCheckpointRepository(
        @NonNull ProcessorCheckpointUpdateEventPublisher processorCheckpointUpdateEventPublisher) {
        super(new InMemoryProcessorCheckpointStore(), processorCheckpointUpdateEventPublisher);
    }

    @Override
    public Collection<ProcessorCheckpoint> findAllBySourceIdentifier(String sourceIdentifier) {
        return processorCheckpointStore.findAllBySourceIdentifier(sourceIdentifier);
    }
}
