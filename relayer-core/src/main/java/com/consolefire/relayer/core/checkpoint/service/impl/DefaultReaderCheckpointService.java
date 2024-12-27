package com.consolefire.relayer.core.checkpoint.service.impl;

import static java.lang.String.format;

import com.consolefire.relayer.core.checkpoint.CheckpointInitializationException;
import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultReaderCheckpointService implements ReaderCheckpointService {

    private final ReaderCheckpointRepository readerCheckpointRepository;

    @Override
    public ReaderCheckpoint initialize(String sourceIdentifier) {
        log.debug("Find reader checkpoint for source: {}", sourceIdentifier);
        ReaderCheckpoint current = readerCheckpointRepository.findByIdentifier(sourceIdentifier);
        if (null == current) {
            log.info("No checkpoint present for source: [{}]. Initializing new checkpoint", sourceIdentifier);
            current = new ReaderCheckpoint(sourceIdentifier, false);
            log.debug("Inserting reader checkpoint for source: [{}]", current);
            return readerCheckpointRepository.insert(current);
        }
        log.debug("Checkpoint exists: {}", current);
        throw new CheckpointInitializationException();
    }

    @Override
    public ReaderCheckpoint complete(String sourceIdentifier) {
        log.debug("Complete reader checkpoint for source: {}", sourceIdentifier);
        ReaderCheckpoint current = readerCheckpointRepository.findByIdentifier(sourceIdentifier);
        if (null == current) {
            log.info("No checkpoint present for source: [{}]. Nothing to complete", sourceIdentifier);
            return null;
        }
        return readerCheckpointRepository.update(current.withCompleted(true));
    }

    @Override
    public ReaderCheckpoint reset(String sourceIdentifier) {
        log.debug("Reset reader checkpoint for source: {}", sourceIdentifier);
        ReaderCheckpoint current = readerCheckpointRepository.findByIdentifier(sourceIdentifier);
        if (null == current) {
            log.info("No checkpoint present for source: [{}]. Nothing to reset", sourceIdentifier);
            return null;
        }
        return readerCheckpointRepository.update(
            new ReaderCheckpoint(sourceIdentifier, false));
    }

    @Override
    public void delete(String sourceIdentifier) {
        log.debug("Delete reader checkpoint for source: {}", sourceIdentifier);
        readerCheckpointRepository.deleteByIdentifier(sourceIdentifier);
    }

    @Override
    public boolean validate(String sourceIdentifier) {
        log.debug("Validate reader checkpoint for source: {}", sourceIdentifier);
        ReaderCheckpoint current = readerCheckpointRepository.findByIdentifier(sourceIdentifier);
        if (current == null) {
            return true;
        }
        log.debug("Current checkpoint: {}", current);
        return current.isCompleted() || current.isExpired();
    }
}
