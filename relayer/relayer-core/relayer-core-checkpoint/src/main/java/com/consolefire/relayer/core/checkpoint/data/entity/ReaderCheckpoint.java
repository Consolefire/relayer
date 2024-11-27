package com.consolefire.relayer.core.checkpoint.data.entity;

import java.time.Instant;

public record ReaderCheckpoint(String sourceIdentifier, boolean completed, Instant createdAt, Instant expiresAt) {
    public static final long DEFAULT_EXPIRES_IN_TIME_MILLIS = 60 * 1000; // 60 seconds

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ReaderCheckpoint{");
        sb.append("sourceIdentifier='").append(sourceIdentifier).append('\'');
        sb.append(", completed=").append(completed);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append('}');
        return sb.toString();
    }
}
