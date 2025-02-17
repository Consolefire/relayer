package com.consolefire.relayer.model;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@FieldNameConstants(asEnum = true)
public abstract class ParkedMessage<ID extends Serializable, M extends Message<ID>> implements ParkableMessage<ID, M> {

    private M message;
    private Instant parkedAt;

    public ParkedMessage(M message, Instant parkedAt) {
        this.message = message;
        this.parkedAt = parkedAt;
    }
}
