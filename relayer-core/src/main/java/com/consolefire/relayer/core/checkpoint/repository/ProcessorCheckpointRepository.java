package com.consolefire.relayer.core.checkpoint.repository;

import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import java.util.Collection;

public interface ProcessorCheckpointRepository {

    Collection<ProcessorCheckpoint> findAllBySourceIdentifier(String readerIdentifier);
}
