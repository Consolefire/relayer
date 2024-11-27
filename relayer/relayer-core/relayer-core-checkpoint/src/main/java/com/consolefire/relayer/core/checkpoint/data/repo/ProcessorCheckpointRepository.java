package com.consolefire.relayer.core.checkpoint.data.repo;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;

import java.util.Collection;
import java.util.UUID;

public interface ProcessorCheckpointRepository extends CheckpointRepository<UUID, ProcessorCheckpoint> {

    Collection<ProcessorCheckpoint> findAllByReference(String referenceIdentifier);

    void deleteAllByReference(String referenceIdentifier);

}
