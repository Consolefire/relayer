package com.consolefire.relayer.core.checkpoint.service;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;

public interface ReaderCheckpointService {

    ReaderCheckpoint findByIdentifier(String readerIdentifier);

    ReaderCheckpoint save(ReaderCheckpoint readerCheckpoint);

    ReaderCheckpoint reset(ReaderCheckpoint readerCheckpoint);
}
