package com.consolefire.relayer.core.checkpoint;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public abstract class Checkpoint<ID extends Serializable> {

    protected static final long DEFAULT_EXPIRY_AFTER = 30;


    protected final ID identifier;
    protected final boolean completed;
    protected final Instant createdAt;
    protected final Instant expiresAt;

    public Checkpoint(@NonNull ID identifier) {
        this(identifier, false);
    }

    public Checkpoint(@NonNull ID identifier, boolean completed) {
        this.identifier = identifier;
        this.completed = completed;
        this.createdAt = Instant.now();
        this.expiresAt = this.createdAt.plus(DEFAULT_EXPIRY_AFTER, ChronoUnit.SECONDS);
    }

    public Checkpoint(@NonNull ID identifier, boolean completed, Instant createdAt) {
        this(identifier, completed, createdAt, createdAt.plus(DEFAULT_EXPIRY_AFTER, ChronoUnit.SECONDS));
    }

    public Checkpoint(@NonNull ID identifier, boolean completed, Instant createdAt, Instant expiresAt) {
        this.identifier = identifier;
        this.completed = completed;
        this.createdAt = Optional.ofNullable(createdAt).orElse(Instant.now());
        this.expiresAt = Optional.ofNullable(expiresAt)
            .orElse(this.createdAt.plus(DEFAULT_EXPIRY_AFTER, ChronoUnit.SECONDS));
    }

    public boolean isExpired() {
        final Instant now = Instant.now();
        return now.equals(expiresAt) || now.isAfter(expiresAt);
    }

    public abstract Checkpoint<ID> withCompleted(boolean isCompleted);
}
