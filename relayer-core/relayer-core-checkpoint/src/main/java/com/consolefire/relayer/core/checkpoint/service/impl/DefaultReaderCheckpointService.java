package com.consolefire.relayer.core.checkpoint.service.impl;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultReaderCheckpointService implements ReaderCheckpointService {


    @Override
    public ReaderCheckpoint initialize(String sourceIdentifier) {
        return null;
    }

    @Override
    public ReaderCheckpoint complete(String sourceIdentifier) {
        return null;
    }

    @Override
    public ReaderCheckpoint reset(String sourceIdentifier) {
        return null;
    }

    @Override
    public void delete(String sourceIdentifier) {

    }

    @Override
    public boolean validate(String readerIdentifier) {
        return false;
    }
}
