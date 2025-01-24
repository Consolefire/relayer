package com.consolefire.relayer.core.checkpoint.queue.consumer;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import com.consolefire.relayer.core.checkpoint.event.ProcessorCheckpointUpdateEvent;
import com.consolefire.relayer.core.checkpoint.queue.ProcessorCheckpointUpdateEventQueue;
import com.consolefire.relayer.core.checkpoint.repository.ProcessorCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.util.ConsumerQueueAbstractConsumer;
import java.util.Collection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessorCheckpointUpdateEventConsumer extends
    ConsumerQueueAbstractConsumer<ProcessorCheckpointUpdateEvent> {

    private final ProcessorCheckpointRepository processorCheckpointRepository;
    private final ReaderCheckpointService readerCheckpointService;

    public ProcessorCheckpointUpdateEventConsumer(
        @NonNull ProcessorCheckpointUpdateEventQueue checkpointUpdateEventQueue,
        @NonNull ProcessorCheckpointRepository processorCheckpointRepository,
        @NonNull ReaderCheckpointService readerCheckpointService) {
        super(checkpointUpdateEventQueue);
        this.processorCheckpointRepository = processorCheckpointRepository;
        this.readerCheckpointService = readerCheckpointService;
    }

    @Override
    protected void processElement(ProcessorCheckpointUpdateEvent event) {
        log.debug("Processing : {}", event);
        Collection<ProcessorCheckpoint> processorCheckpoints = processorCheckpointRepository.findAllBySourceIdentifier(
            event.getSourceIdentifier());
        if (null == processorCheckpoints || processorCheckpoints.isEmpty()) {
            log.warn("No checkpoints found for source: {}", event.getSourceIdentifier());
            return;
        }
        if (processorCheckpoints.size() < event.getTotal()) {
            log.warn("Incomplete checkpoint count: [{}] for source: {}", processorCheckpoints.size(),
                event.getSourceIdentifier());
            return;
        }
        boolean completed = processorCheckpoints.stream()
            .allMatch(e -> e.isCompleted());
        if (completed) {
            log.debug("All checkpoint completed for source: {}", event.getSourceIdentifier());
            readerCheckpointService.complete(event.getSourceIdentifier());
        }
    }

}
