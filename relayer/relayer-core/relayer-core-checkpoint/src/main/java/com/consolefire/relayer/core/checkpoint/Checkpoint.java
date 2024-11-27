package com.consolefire.relayer.core.checkpoint;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@SuperBuilder
public abstract class Checkpoint<ID extends Serializable> {

    protected final ID identifier;
    protected final String referenceCheckpointId;

    public Checkpoint(@NonNull ID identifier, String referenceCheckpointId) {
        this.identifier = identifier;
        this.referenceCheckpointId = referenceCheckpointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkpoint<?> that)) return false;
        return Objects.equals(identifier, that.identifier)
                && Objects.equals(referenceCheckpointId, that.referenceCheckpointId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, referenceCheckpointId);
    }
}
