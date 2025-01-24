package com.consolefire.relayer.core.checkpoint.store;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProcessorCheckpointStore implements ProcessorCheckpointStore {

    private final ConcurrentHashMap<String, Set<ProcessorCheckpoint>> processorCheckpoints = new ConcurrentHashMap<>();

    @Override
    public Collection<ProcessorCheckpoint> findAllBySourceIdentifier(String sourceIdentifier) {
        Set<ProcessorCheckpoint> set = processorCheckpoints.get(sourceIdentifier);
        if (null == set || set.isEmpty()) {
            return List.of();
        }
        return set.stream().toList();
    }
}
