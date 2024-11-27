package com.consolefire.relayer.core.checkpoint.data.repo.impl;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.data.repo.ReaderCheckpointRepository;

public class BaseReaderCheckpointRepository
        extends AbstractCheckpointRepository<String, ReaderCheckpoint>
        implements ReaderCheckpointRepository {

    @Override
    public ReaderCheckpoint findByIdentifier(String identifier, boolean includeReferences) {
        return null;
    }
}
