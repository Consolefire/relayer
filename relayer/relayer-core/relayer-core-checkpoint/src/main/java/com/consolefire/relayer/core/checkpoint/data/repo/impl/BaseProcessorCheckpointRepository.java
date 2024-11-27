package com.consolefire.relayer.core.checkpoint.data.repo.impl;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import com.consolefire.relayer.core.checkpoint.data.repo.ProcessorCheckpointRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BaseProcessorCheckpointRepository
        extends AbstractCheckpointRepository<UUID, ProcessorCheckpoint>
        implements ProcessorCheckpointRepository {


    @Override
    public Collection<ProcessorCheckpoint> findAllByReference(String referenceIdentifier) {
        return List.of();
    }

    @Override
    public void deleteAllByReference(String referenceIdentifier) {

    }
}
