package com.consolefire.relayer.core.checkpoint.service;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;

public interface ReaderCheckpointService {

    ReaderCheckpoint initialize(String sourceIdentifier);

    ReaderCheckpoint complete(String sourceIdentifier);

    ReaderCheckpoint reset(String sourceIdentifier);

    void delete(String sourceIdentifier);

    boolean validate(String readerIdentifier);

}
