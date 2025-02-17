package com.consolefire.relayer.model;

import java.io.Serializable;
import java.time.Instant;

public interface ParkableMessage<ID extends Serializable, M extends Message<ID>> {

    M getMessage();

    void setMessage(M message);

    Instant getParkedAt();

    void setParkedAt(Instant parkedAt);

}
