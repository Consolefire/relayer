package com.consolefire.relayer.core.checkpoint.service.impl;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.data.repo.ReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class DefaultReaderCheckpointService implements ReaderCheckpointService {

    private final ReaderCheckpointRepository readerCheckpointRepository;

    @Override
    public ReaderCheckpoint findByIdentifier(String readerIdentifier) {
        return null;
    }

    @Override
    public ReaderCheckpoint save(ReaderCheckpoint readerCheckpoint) {
        return null;
    }

    @Override
    public ReaderCheckpoint reset(ReaderCheckpoint readerCheckpoint) {
        return null;
    }
}
