package com.consolefire.relayer.core.checkpoint.repository.impl;

import com.consolefire.relayer.core.checkpoint.event.ProcessorCheckpointUpdateEventPublisher;
import com.consolefire.relayer.core.checkpoint.repository.ProcessorCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.store.ProcessorCheckpointStore;
import lombok.NonNull;

public abstract class AbstractProcessorCheckpointRepository implements ProcessorCheckpointRepository {

    protected final ProcessorCheckpointStore processorCheckpointStore;
    protected final ProcessorCheckpointUpdateEventPublisher processorCheckpointUpdateEventPublisher;

    public AbstractProcessorCheckpointRepository(
        @NonNull ProcessorCheckpointStore processorCheckpointStore,
        @NonNull ProcessorCheckpointUpdateEventPublisher processorCheckpointUpdateEventPublisher) {
        this.processorCheckpointStore = processorCheckpointStore;
        this.processorCheckpointUpdateEventPublisher = processorCheckpointUpdateEventPublisher;
    }



}
