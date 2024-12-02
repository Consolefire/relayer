package com.consolefire.relayer.core.checkpoint;

import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;

public class ReaderCheckpoint extends Checkpoint<String> {

    public ReaderCheckpoint(@NonNull String identifier) {
        super(identifier);
    }

    public ReaderCheckpoint(@NonNull String identifier, boolean completed) {
        super(identifier, completed);
    }

    public ReaderCheckpoint(@NonNull String identifier, boolean completed, Instant createdAt) {
        super(identifier, completed, createdAt);
    }

    @Builder
    public ReaderCheckpoint(String identifier, boolean completed, Instant createdAt,
        Instant expiresAt) {
        super(identifier, completed, createdAt, expiresAt);
    }

    @Override
    public ReaderCheckpoint withCompleted(boolean isCompleted) {
        return new ReaderCheckpoint(identifier, isCompleted, createdAt, expiresAt);
    }
}
