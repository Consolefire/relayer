package com.consolefire.relayer.outbox.model;

import com.consolefire.relayer.model.ParkedMessage;
import java.io.Serializable;
import java.time.Instant;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SidelinedMessage<ID extends Serializable> extends ParkedMessage<ID, OutboundMessage<ID>> {

    @Builder
    public SidelinedMessage(OutboundMessage<ID> message, Instant parkedAt) {
        super(message, parkedAt);
    }
}


