package com.consolefire.relayer.core.checkpoint;

import java.io.Serializable;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public abstract class Checkpoint<ID extends Serializable> {

    protected final ID identifier;
    protected final boolean completed;
    protected final Instant createdAt;
    protected final Instant expiresAt;

    public Checkpoint(ID identifier, boolean completed, Instant createdAt, Instant expiresAt) {
        this.identifier = identifier;
        this.completed = completed;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
