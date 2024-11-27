package com.consolefire.relayer.core.checkpoint.data.repo;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;

public interface ReaderCheckpointRepository extends CheckpointRepository<String, ReaderCheckpoint> {

    ReaderCheckpoint findByIdentifier(String identifier, boolean includeReferences);

}
