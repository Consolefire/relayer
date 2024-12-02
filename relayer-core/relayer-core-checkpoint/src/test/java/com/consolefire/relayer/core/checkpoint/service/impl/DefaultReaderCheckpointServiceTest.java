package com.consolefire.relayer.core.checkpoint.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.consolefire.relayer.core.checkpoint.CheckpointInitializationException;
import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultReaderCheckpointServiceTest {

    private static final String SOURCE_ID = UUID.randomUUID().toString();

    @Mock
    private ReaderCheckpointRepository readerCheckpointRepository;

    private ReaderCheckpointService readerCheckpointService;

    @BeforeEach
    void setUp() {
        readerCheckpointService = new DefaultReaderCheckpointService(readerCheckpointRepository);
    }

    @Test
    void shouldInitializeWhenNotExists() {
        ReaderCheckpoint actualCheckpoint = new ReaderCheckpoint(SOURCE_ID);
        when(readerCheckpointRepository.findByIdentifier(SOURCE_ID)).thenReturn(null);
        when(readerCheckpointRepository.insert(any(ReaderCheckpoint.class)))
            .thenReturn(actualCheckpoint);
        ReaderCheckpoint checkpoint = readerCheckpointService.initialize(SOURCE_ID);

        assertAll(
            () -> assertNotNull(checkpoint),
            () -> assertEquals(SOURCE_ID, checkpoint.getIdentifier()),
            () -> assertFalse(checkpoint.isCompleted())
        );
    }

    @Test
    void shouldNotInitializeWhenExists() {
        ReaderCheckpoint actualCheckpoint = new ReaderCheckpoint(SOURCE_ID);
        when(readerCheckpointRepository.findByIdentifier(SOURCE_ID)).thenReturn(actualCheckpoint);
        assertThrows(CheckpointInitializationException.class, () -> readerCheckpointService.initialize(SOURCE_ID));
    }

    @Test
    void shouldCompleteWhenExists() {
        ReaderCheckpoint expectedCheckpoint = new ReaderCheckpoint(SOURCE_ID);
        assertFalse(expectedCheckpoint.isCompleted());
        when(readerCheckpointRepository.findByIdentifier(SOURCE_ID)).thenReturn(expectedCheckpoint);
        ReaderCheckpoint actualCheckpoint = readerCheckpointService.complete(SOURCE_ID);
        assertAll(
            () -> assertNotNull(actualCheckpoint),
            () -> assertEquals(SOURCE_ID, actualCheckpoint.getIdentifier()),
            () -> assertTrue(actualCheckpoint.isCompleted())
        );
    }

    @Test
    void reset() {
    }

    @Test
    void delete() {
    }

    @Test
    void validate() {
    }
}