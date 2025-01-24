package com.consolefire.relayer.core.checkpoint.event;

public interface ProcessorCheckpointUpdateEventPublisher {

    void publish(ProcessorCheckpointUpdateEvent event);

}
