package com.consolefire.relayer.core.checkpoint;

import java.time.Instant;
import lombok.Builder;

public class ReaderCheckpoint extends Checkpoint<String> {

    @Builder
    public ReaderCheckpoint(String identifier, boolean completed, Instant createdAt,
        Instant expiresAt) {
        super(identifier, completed, createdAt, expiresAt);
    }
}
