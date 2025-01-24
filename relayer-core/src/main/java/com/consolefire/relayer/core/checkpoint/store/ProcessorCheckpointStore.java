package com.consolefire.relayer.core.checkpoint.store;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import java.util.Collection;

public interface ProcessorCheckpointStore {

    Collection<ProcessorCheckpoint> findAllBySourceIdentifier(String sourceIdentifier);
}
